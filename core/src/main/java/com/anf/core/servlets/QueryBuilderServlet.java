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
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

@Component(service = { Servlet.class })
@SlingServletPaths(
        value = "/bin/queryBuilder"
)
public class QueryBuilderServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryBuilderServlet.class);

    private static final String PATH_BASE_PATH = "/content/anf-code-challenge/us/en";


    @Reference
	private QueryBuilder builder;
    
    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
    	try {
    		Map<String, String> predicate = new HashMap<>();

	
			predicate.put("path", PATH_BASE_PATH);
			predicate.put("type", "cq:Page");
			predicate.put("property", "jcr:content/anfCodeChallenge");
			predicate.put("property.operation", "exists");

			ResourceResolver resourceResolver = req.getResourceResolver();
			Session session = resourceResolver.adaptTo(Session.class);
			Query query = builder.createQuery(PredicateGroup.create(predicate), session);
			
			SearchResult searchResult = query.getResult();
			
			for(Hit hit : searchResult.getHits()) {
				
				String path = hit.getPath();
				
				resp.getWriter().println(path);
			}
    	}catch(Exception exception) {
    		LOGGER.error("Error :" + exception);
    	}
    }
}
