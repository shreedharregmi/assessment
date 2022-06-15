package com.anf.core.services.impl;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anf.core.models.UserDetails;
import com.anf.core.services.ContentService;

@Component(immediate = true, service = ContentService.class)
public class ContentServiceImpl implements ContentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

	private static final String BASE_PATH = "/var/anf-code-challenge";
    @Override
    public void commitUserDetails(UserDetails userDetails, ResourceResolver resourceResolver) {
    	try {
    		Session session = resourceResolver.adaptTo(Session.class);
        	Node baseNode = session.getNode(BASE_PATH);
        	String nodeName = "user_" + System.currentTimeMillis();
        	Node newNode = baseNode.addNode(nodeName);
        	newNode.setProperty("firstName", userDetails.getFirstName());
        	newNode.setProperty("lastName", userDetails.getLastName());
        	newNode.setProperty("age", userDetails.getAge());
        	newNode.setProperty("contry", userDetails.getContry());
        	session.save();
    	}catch(RepositoryException exception) {
    		LOGGER.error("Error in commitUserDetails:" + exception);
    	}
    	
    }
}
