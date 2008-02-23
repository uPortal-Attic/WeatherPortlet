package org.jasig.portlet.weather.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;
import org.springframework.web.portlet.util.PortletUtils;

public class WeatherAddController extends SimpleFormController {
	
	@Override
	protected void processFormSubmission(ActionRequest request,
			ActionResponse response, Object command, BindException errors)
			throws Exception {
		if (request.getParameter("searchAgainSubmit") != null) {
			PortletUtils.clearAllRenderParameters(response);
			return;
		}
		
		super.processFormSubmission(request, response, command, errors);
	}

	@Override
	protected void onSubmitAction(ActionRequest request,
			ActionResponse response, Object command, BindException errors)
			throws Exception {
		WeatherEditCommand formData = (WeatherEditCommand)command;
		PortletPreferences prefs = request.getPreferences();
		String[] locationCodes = prefs.getValues("locationCodes", null);
		String[] metrics = prefs.getValues("metrics", null);
		String[] newLocationCodes = null;
		String[] newMetrics = null;
		if (formData.getLocationCode() != null && formData.getLocationCode().length() > 0) {
			if (locationCodes != null) {
				int length = locationCodes.length;
				newLocationCodes = new String[length + 1];
				System.arraycopy(locationCodes, 0, newLocationCodes, 0, length);
				newLocationCodes[length] = formData.getLocationCode();
			} else {
				newLocationCodes = new String[1];
				newLocationCodes[0] = formData.getLocationCode();
			}
			prefs.setValues("locationCodes", newLocationCodes);
			if (metrics != null) {
				int length = metrics.length;
				newMetrics = new String[length + 1];
				System.arraycopy(metrics, 0, newMetrics, 0, length);
				newMetrics[length] = formData.getMetric();
			} else {
				newMetrics = new String[1];
				newMetrics[0] = formData.getMetric();
			}
			prefs.setValues("metrics", newMetrics);
			prefs.store();
		}
	}

}
