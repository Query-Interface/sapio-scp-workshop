package com.queryinterface.sapioscpworkshop.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfiguration {
	
	private static final String API_KEY = "API_KEY";
	
	public Optional<String> getApiKey() {
		try {
			String apiKey = System.getenv(API_KEY);
			return Optional.of(apiKey);
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}
