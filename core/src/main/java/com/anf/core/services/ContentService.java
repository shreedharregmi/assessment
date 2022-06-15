package com.anf.core.services;

import org.apache.sling.api.resource.ResourceResolver;

import com.anf.core.models.UserDetails;

public interface ContentService {
	void commitUserDetails(UserDetails userDetails, ResourceResolver resourceResolver);
}
