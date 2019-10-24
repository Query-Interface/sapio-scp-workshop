package com.queryinterface.sapioscpworkshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.queryinterface.sapioscpworkshop.service.FeatureFlagsService;

/**
 * Represents a configuration class for creating {@link FeatureFlagsService}
 * instance for both cloud and local environment.
 */

@Configuration
public class CloudConfig extends AbstractCloudConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(CloudConfig.class);

	private static final String NO_FEATURE_FLAGS_SERVICE_INSTANCE_FOUND_MESSAGE = "There is no Feature Flags service instance bound to the application.";
	private static final String NO_CLOUD_FACTORY_FOUND_MESSAGE = "There is no Cloud Facory available. Check that your application is running on CF or that VCP_APPLICATION and VCAP_SERVICES environment variables are declared in your test environement";

	
	/**
	 * Creates a {@link FeatureFlagsService} for both cloud and local
	 * environment. If there is no Feature Flags instance bound to the
	 * application in the cloud, then {@code null} is returned.
	 * 
	 * @return instance of {@link FeatureFlagsService}
	 */

	@Bean
	public FeatureFlagsService featureFlagsService() {
		try {
			return connectionFactory().service(FeatureFlagsService.class);
		} catch (CloudException e) {
			LOGGER.error(NO_FEATURE_FLAGS_SERVICE_INSTANCE_FOUND_MESSAGE, e);
		} catch (Exception e) {
			LOGGER.warn(NO_CLOUD_FACTORY_FOUND_MESSAGE, e);
		}
		return null;
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// For the purpose of the training, the exception is hidden to allow starting the application even if the feature-flag service is not available.
		// It allows to to demonstrate the /api/projects endpoints in the first exercise without issue.
		// The /api/ff endpoint will also be accessible but will always return a status that specify that the feature-flag service is missing.
		try {
			super.setBeanFactory(beanFactory);
		} catch (CloudException ce) {
			LOGGER.warn(NO_CLOUD_FACTORY_FOUND_MESSAGE, ce);
		}
	}
}
