package com.queryinterface.sapioscpworkshop.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {
	
	private static final String API_KEY = "API_KEY";

	Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	public Optional<String> getApiKey() {
		try {
			String apiKey = System.getenv(API_KEY);
			return Optional.of(apiKey);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Unable to read environment variable 'API_KEY'", e);
			}
			return Optional.empty();
		}
	}
}
