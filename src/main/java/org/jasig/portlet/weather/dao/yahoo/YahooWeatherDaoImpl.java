package org.jasig.portlet.weather.dao.yahoo;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.CacheEntryFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.jasig.portlet.weather.QuietUrlCodec;
import org.jasig.portlet.weather.TemperatureUnit;
import org.jasig.portlet.weather.dao.IWeatherDao;
import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataRetrievalFailureException;

public class YahooWeatherDaoImpl implements IWeatherDao, DisposableBean, InitializingBean, CacheEntryFactory {

    private static final String FIND_URL = "http://where.yahooapis.com/v1/places.q(@QUERY@);count=10?appid=@KEY@";
    private static final String WEATHER_URL = "http://weather.yahooapis.com/forecastrss?w=@LOCATION@&u=@UNIT@";

    private IYahooWeatherParsingService weatherParsingService;
    
    public void setWeatherParsingService(IYahooWeatherParsingService parser) {
        this.weatherParsingService = parser;
    }
    
    private IYahooLocationParsingService locationParsingService;
    
    public void setLocationParsingService(IYahooLocationParsingService parser) {
        this.locationParsingService = parser;
    }
    
    private String key;
    
    public void setKey(String key) {
        this.key = key;
    }

    //Multi-threaded connection manager for exclusive access
    private final MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
    
    //Define the HttpClient here and pass it so we only define one instance
    private final HttpClient httpClient = new HttpClient(connectionManager);
    
    private Ehcache weatherDataCache;
    private Ehcache weatherDataErrorCache;

    //Default timeout of 5 seconds
    private int connectionTimeout = 5000;
    
    //Default timeout of 5 seconds
    private int readTimeout = 5000;
    
    //Default retry of 5 times
    private int timesToRetry = 5;


    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
    
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setTimesToRetry(int timesToRetry) {
        this.timesToRetry = timesToRetry;
    }
    
    public void setWeatherDataCache(Ehcache ehcache) {
        this.weatherDataCache = ehcache;
    }

    public void setWeatherDataErrorCache(Ehcache weatherDataErrorCache) {
        this.weatherDataErrorCache = weatherDataErrorCache;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        final HttpConnectionManager httpConnectionManager = httpClient.getHttpConnectionManager();
        final HttpConnectionManagerParams params = httpConnectionManager.getParams();
        params.setConnectionTimeout(connectionTimeout);
        params.setSoTimeout(readTimeout);
        
        params.setParameter(HttpMethodParams.RETRY_HANDLER, new HttpMethodRetryHandler() {
            public boolean retryMethod(final HttpMethod method, final IOException exception, int executionCount) {
                if (executionCount >= timesToRetry) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // Retry if the server dropped connection on us
                    return true;
                }
                if (exception instanceof SocketException) {
                    // Retry if the server reset connection on us
                    return true;
                }
                if (exception instanceof SocketTimeoutException) {
                    // Retry if the read timed out
                    return true;
                }
                if (!method.isRequestSent()) {
                    // Retry if the request has not been sent fully or
                    // if it's OK to retry methods that have been sent
                    return true;
                }
                // otherwise do not retry
                return false;
            }
        });
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {
        this.connectionManager.shutdown();
    }
    
    /* (non-Javadoc)
     * @see net.sf.ehcache.constructs.blocking.CacheEntryFactory#createEntry(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public Object createEntry(Object o) throws Exception {
        final Map<String, Object> key = (Map<String, Object>)o;
        this.checkCachedException(key);
        
        final String locationCode = (String)key.get("locationCode");
        final TemperatureUnit unit = (TemperatureUnit)key.get("unit");
        
        final String yahooweatherUrl = WEATHER_URL.replace("@LOCATION@", QuietUrlCodec.encode(locationCode, Constants.URL_ENCODING)).replace("@UNIT", (TemperatureUnit.C.equals(unit) ? "c" : "f"));

        Weather weather = null;
        try {
            weather = (Weather)this.getAndDeserialize(yahooweatherUrl);
        }
        catch (RuntimeException e) {
            final DataRetrievalFailureException drfe = new DataRetrievalFailureException("get weather failed for location '" + locationCode + "' and unit " + unit, e);
            
            //Cache the exception to avoid retrying a 'bad' data feed too frequently
            final Element element = new Element(key, drfe);
            this.weatherDataErrorCache.put(element);
            
            throw drfe;
        }

        return weather;
    }

    /*
     * (non-Javadoc)
     * @see org.jasig.portlet.weather.dao.IWeatherDao#find(java.lang.String)
     */
    public Collection<Location> find(String location) {
        final String url = FIND_URL.replace("@KEY@", key).replace("@QUERY@", QuietUrlCodec.encode(location, Constants.URL_ENCODING));
        
        HttpMethod getMethod = new GetMethod(url);
        InputStream inputStream = null;
        try {
            // Execute the method.
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                final String statusText = getMethod.getStatusText();
                throw new DataRetrievalFailureException("get of '" + url + "' failed with status '" + statusCode + "' due to '" + statusText + "'");
            }

            // Read the response body
            inputStream = getMethod.getResponseBodyAsStream();
            
            List<Location> locations = locationParsingService.parseLocations(inputStream);
 
            return locations;
            
        } catch (HttpException e) {
            throw new RuntimeException("http protocol exception while getting data from weather service from: " + url, e);
        } catch (IOException e) {
            throw new RuntimeException("IO exception while getting data from weather service from: " + url, e);
        } finally {
            //try to close the inputstream
            IOUtils.closeQuietly(inputStream);
            //release the connection
            getMethod.releaseConnection();
        }       
    }

    /* (non-Javadoc)
     * @see org.jasig.portlet.weather.dao.IWeatherDao#getWeather(java.lang.String, org.jasig.portlet.weather.TemperatureUnit)
     */
    public Weather getWeather(String locationCode, TemperatureUnit unit) {
        final Map<String, Object> key = new LinkedHashMap<String, Object>();
        key.put("locationCode", locationCode);
        key.put("unit", unit);
        this.checkCachedException(key);
        
        final Element element = this.weatherDataCache.get(key);
        return (Weather)element.getValue();
    }

    protected void checkCachedException(final Map<String, Object> key) {
        final Element errorElement = this.weatherDataErrorCache.get(key);
        if (errorElement != null && !errorElement.isExpired()) {
            throw (RuntimeException)errorElement.getValue();
        }
    }
    
    protected Object getAndDeserialize(String url) {
        HttpMethod getMethod = new GetMethod(url);
        InputStream inputStream = null;
        try {
            // Execute the method.
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                final String statusText = getMethod.getStatusText();
                throw new DataRetrievalFailureException("get of '" + url + "' failed with status '" + statusCode + "' due to '" + statusText + "'");
            }

            // Read the response body
            inputStream = getMethod.getResponseBodyAsStream();
            return weatherParsingService.parseWeather(inputStream);
        } catch (HttpException e) {
            throw new RuntimeException("http protocol exception while getting data from weather service from: " + url, e);
        } catch (IOException e) {
            throw new RuntimeException("IO exception while getting data from weather service from: " + url, e);
        } finally {
            //try to close the inputstream
            IOUtils.closeQuietly(inputStream);
            //release the connection
            getMethod.releaseConnection();
        }       
    }

}
