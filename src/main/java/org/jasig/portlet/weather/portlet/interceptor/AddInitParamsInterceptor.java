package org.jasig.portlet.weather.portlet.interceptor;

import java.util.Enumeration;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.context.PortletConfigAware;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor that adds init-params defined in portlet.xml prefixed by default
 * with <CODE>model:</CODE> for use in the model. The prefix is simply a marker
 * and the init-param will be available to the model as the param-name without
 * the prefix.
 * 
 * @author Dustin Schultz
 * @version $Id$
 * @since August 8th, 2008
 */
public class AddInitParamsInterceptor extends HandlerInterceptorAdapter
		implements PortletConfigAware {

	/**
	 * PortletConfig object which holds init-params
	 */
	private PortletConfig portletConfig;

	/**
	 * Logger
	 */
	private Logger log = Logger.getLogger(getClass());

	/**
	 * The prefix to look for when adding to the model
	 */
	private String modelPrefix = "model:";

	/**
	 * Adds init-params to the model. The init-params are available to the model
	 * as the param-name without the prefix.
	 * 
	 * @see org.springframework.web.portlet.handler.HandlerInterceptorAdapter#postHandleRender(javax.portlet.RenderRequest,
	 *      javax.portlet.RenderResponse, java.lang.Object,
	 *      org.springframework.web.portlet.ModelAndView)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void postHandleRender(RenderRequest request,
			RenderResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		Enumeration<String> initNames = portletConfig.getInitParameterNames();
		while (initNames.hasMoreElements()) {
			String initParamName = initNames.nextElement();
			if (!initParamName.startsWith(modelPrefix)) {
				continue;
			}
			Object initParamValue = portletConfig
					.getInitParameter(initParamName);
			initParamName = initParamName.replace(modelPrefix, "");
			if (log.isDebugEnabled()) {
				log.debug("Adding init-param [" + initParamName + ":"
						+ initParamValue + "] to model");
			}
			modelAndView.addObject(initParamName, initParamValue);
		}
	}

	/**
	 * @see
	 * org.springframework.web.portlet.context.PortletConfigAware#setPortletConfig
	 * (javax.portlet.PortletConfig)
	 */
	public void setPortletConfig(PortletConfig portletConfig) {
		this.portletConfig = portletConfig;
	}

	/**
	 * Allows for overriding the default modelPrefix
	 * 
	 * @param modelPrefix
	 */
	public void setModelPrefix(String modelPrefix) {
		this.modelPrefix = modelPrefix;
	}

}
