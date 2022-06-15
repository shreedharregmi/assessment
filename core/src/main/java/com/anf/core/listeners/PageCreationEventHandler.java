package com.anf.core.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jcr.LoginException;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = EventHandler.class, property = {
		Constants.SERVICE_DESCRIPTION + "= This event handler listens the events on page Creation",
		EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
		EventConstants.EVENT_FILTER + "=(&(path=/content/anf-code-challenge/us/en/*)(resourceType=cq:Page))" })
public class PageCreationEventHandler implements EventHandler {

	public static final String CONST_SLASH_JCR_CONTENT = "/jcr:content";

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Reference
	private SlingSettingsService settingsService;

	private static final Logger log = LoggerFactory.getLogger(PageCreationEventHandler.class);

	@Override
	public void handleEvent(Event event) {

		if (!isAuthoringInstance()) {
			log.debug("since this is not authoring instance, coming out from the event");
			return;
		}

		try {
			ResourceResolver resolver = getServiceResourceResolver();
			String pagePath = event.getProperty("path").toString();
			log.debug("Created Page Path" + pagePath);
			Resource resource = resolver.getResource(pagePath + CONST_SLASH_JCR_CONTENT);
			ModifiableValueMap properties = resource.adaptTo(ModifiableValueMap.class);
			
			properties.put("pageCreated", true);
			resolver.commit();
		} catch (PersistenceException e) {
			log.error("PersistenceException in handleEvent", e);
		} catch (org.apache.sling.api.resource.LoginException e) {
			log.error("LoginException in handleEvent", e);
		} catch (LoginException e) {
			log.error("LoginException in handleEvent", e);
		}
	}

	public boolean isAuthoringInstance() {
		boolean isAuthoringInstance = false;
		log.debug("checking run mode of instance");
		final Set<String> runmodes = settingsService.getRunModes();
		for (String runmode : runmodes) {
			if (runmode.equalsIgnoreCase("author")) {
				isAuthoringInstance = true;
			}
		}
		log.debug("is author runmode : " + isAuthoringInstance);
		return isAuthoringInstance;
	}

	public ResourceResolver getServiceResourceResolver() throws LoginException, org.apache.sling.api.resource.LoginException {
		Map<String, Object> param = new HashMap<>();
		param.put(ResourceResolverFactory.SUBSERVICE, "adminService");
		return resourceResolverFactory.getServiceResourceResolver(param);
	}
}
