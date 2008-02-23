package org.jasig.portlet.weather.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;

public class WeatherDeleteController extends SimpleFormController {

	@Override
	protected void onSubmitAction(ActionRequest request,
			ActionResponse response, Object command, BindException errors)
			throws Exception {
		// TODO Auto-generated method stub
		super.onSubmitAction(request, response, command, errors);
	}

}
