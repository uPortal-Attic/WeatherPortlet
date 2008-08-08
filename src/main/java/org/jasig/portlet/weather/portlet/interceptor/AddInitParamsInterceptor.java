package org.jasig.portlet.weather.portlet.interceptor;

import java.util.Enumeration;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.context.PortletConfigAware;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

public class AddInitParamsInterceptor 
	extends HandlerInterceptorAdapter implements PortletConfigAware {

	private PortletConfig portletConfig;
	
	@SuppressWarnings("unchecked")
	@Override
	public void postHandleRender(RenderRequest request,
			RenderResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		Enumeration<String> initNames = portletConfig.getInitParameterNames(); 
		while (initNames.hasMoreElements()) {
			String initParamName = initNames.nextElement();
			Object initParamValue = portletConfig.getInitParameter(initParamName);
			modelAndView.addObject(initParamName, initParamValue);
		}
	}

	public void setPortletConfig(PortletConfig portletConfig) {
		this.portletConfig = portletConfig;
	}

}
