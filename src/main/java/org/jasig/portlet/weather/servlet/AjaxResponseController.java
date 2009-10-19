/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
 */

package org.jasig.portlet.weather.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.web.service.AjaxPortletSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
@Controller
public class AjaxResponseController {
    private AjaxPortletSupport ajaxPortletSupport;
    
    @Autowired
    public void setAjaxPortletSupport(AjaxPortletSupport ajaxPortletSupport) {
        this.ajaxPortletSupport = ajaxPortletSupport;
    }
    
    @RequestMapping("/json")
    public ModelAndView renderAjaxResponse(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException {
        final Map<Object, Object> model = this.ajaxPortletSupport.getAjaxModel(request, response);
        return new ModelAndView("jsonView", model);
    }
}
