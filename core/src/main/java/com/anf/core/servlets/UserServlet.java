/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.anf.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anf.core.models.UserDetails;
import com.anf.core.services.ContentService;

@Component(service = { Servlet.class })
@SlingServletPaths(
        value = "/bin/saveUserDetails"
)
public class UserServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    private static final String AGE_DATA_PATH = "/etc/age";

    @Reference
    private ContentService contentService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
    	try {
    		JSONObject jsonResp = new JSONObject();
        	boolean isValidAge = validateAge(req);
        	if(isValidAge) {
            	contentService.commitUserDetails(getUserDetailsFromRequest(req), req.getResourceResolver());
    			jsonResp.put("status", "success");
            	resp.getWriter().write(jsonResp.toString());
        	}else {
        		jsonResp.put("error", "invalid age");
        	}
    	}catch(Exception exception) {
    		LOGGER.error("Error :" + exception);
    	}
    }
    private UserDetails getUserDetailsFromRequest(SlingHttpServletRequest req) {
    	String firstName = req.getParameter("firstName");
    	String lastName = req.getParameter("lastName");
    	String age = req.getParameter("age");
    	String contry = req.getParameter("contry");
    	return new UserDetails(firstName, lastName, Integer.parseInt(age), contry);
    }
    private boolean validateAge(SlingHttpServletRequest req) {
    	boolean isValid = false;
    	String age = req.getParameter("age");
    	if(age != null ) {
    		Long ageVal = Long.valueOf(age);
    		ResourceResolver resourceResolver = req.getResourceResolver();
        	Resource ageResource = resourceResolver.getResource(AGE_DATA_PATH);
        	ValueMap map = ageResource.adaptTo(ValueMap.class);
        	Long minAge = map.get("minAge", Long.class);
        	Long maxAge = map.get("maxAge", Long.class);
    		if(ageVal>=minAge && ageVal<=maxAge) {
    			isValid = true;
    		}
    	}
    	return isValid;
    }
}
