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
package com.anf.core.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class)
public class NewsFeedModel {
    
	private static final String NEWS_FEED_PATH = "/var/commerce/products/anf-code-challenge/newsData";
	
	private static final String DATE_DISPLAY_FORMATE = "dd.MM.yyyy";
	
    @SlingObject
    private ResourceResolver resourceResolver;

    private Resource newsFeed;
    
    private String date;

    @PostConstruct
    protected void init() {
        newsFeed = resourceResolver.getResource(NEWS_FEED_PATH);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_DISPLAY_FORMATE);  
        date = formatter.format(Calendar.getInstance().getTime());  
    }

	public Resource getNewsFeed() {
		return newsFeed;
	}

	public String getDate() {
		return date;
	}
	
}
