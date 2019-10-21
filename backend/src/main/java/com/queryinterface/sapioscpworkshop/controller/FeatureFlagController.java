package com.queryinterface.sapioscpworkshop.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.queryinterface.sapioscpworkshop.service.FeatureFlagsService;

@RestController
@RequestMapping("api")
public class FeatureFlagController {

	private Optional<FeatureFlagsService> featureFlagsServiceOptional;
	
	public FeatureFlagController(final Optional<FeatureFlagsService> featureFlagsServiceOptional) {
		this.featureFlagsServiceOptional = featureFlagsServiceOptional;
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/ff/{id}", produces = "application/json")
	public ResponseEntity<Map<String, String>> evaluate(@PathVariable final String id) {
		String status;
		if (!hasBoundServiceInstance()) {
			status = "missing-service-instance";
		} else {
			status = featureFlagsServiceOptional.get().getFlagStatus(id).toString();
		}
		Map<String, String> response = new HashMap<String, String>(2);
		response.put("id", id);
		response.put("status", status);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	private boolean hasBoundServiceInstance() {
		return featureFlagsServiceOptional.isPresent();
	}
}
