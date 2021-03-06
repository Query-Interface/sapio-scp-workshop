package com.queryinterface.sapioscpworkshop.config;

import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.UriBasedServiceInfo;
import org.springframework.util.Assert;

/**
 * Represents a {@link ServiceInfo} for the Feature Flags service.
 */

public class FeatureFlagsServiceInfo extends UriBasedServiceInfo {

	public static final String HTTPS_SCHEME = "https";

	public FeatureFlagsServiceInfo(String id, String host, int port, String username, String password, String path) {
		super(id, HTTPS_SCHEME, host, port, username, password, path);
		Assert.notNull(username, "username not specified");
		Assert.notNull(password, "password not specified");
	}

	public FeatureFlagsServiceInfo(String id, String uriString) {
		super(id, uriString);
	}
}
