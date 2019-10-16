package com.queryinterface.sapioscpworkshop.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.queryinterface.sapioscpworkshop.Project;
import com.queryinterface.sapioscpworkshop.WorkItem;
import com.queryinterface.sapioscpworkshop.Workspace;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataRawRequest;
import org.apache.olingo.client.api.communication.response.ODataRawResponse;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.core.ODataClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.queryinterface.sapioscpworkshop.config.Configuration;
import com.queryinterface.sapioscpworkshop.exception.S4ServerException;
import org.springframework.stereotype.Service;

@Service
public class ODataS4Client {

	private ODataClient client = null;
	private static String projectReadApiEndpoint = "https://sandbox.api.sap.com/s4hanacloud/sap/opu/odata/cpd/SC_EXTERNAL_SERVICES_SRV";
	private static String projectWriteApiEndpoint= "https://sandbox.api.sap.com/s4hanacloud/sap/opu/odata/cpd/SC_PROJ_ENGMT_CREATE_UPD_SRV";

	Configuration configuration = new Configuration();
    Logger logger = LoggerFactory.getLogger(ODataS4Client.class);

	public ODataS4Client() {
		client = ODataClientFactory.getClient();
	}

	public List<Project> getProjects() {
		List<Project> projects;
		try {
			ODataEntitySetRequest<ClientEntitySet> request = this.client.getRetrieveRequestFactory().getEntitySetRequest(new URI(projectReadApiEndpoint + "/ProjectSet"));
			request.addCustomHeader("apikey", configuration.getApiKey().orElseThrow(() -> new S4ServerException()));
			request.addCustomHeader("Accept", "application/xml");
			ODataRetrieveResponse<ClientEntitySet> response = request.execute();
			List<ClientEntity> entities = response.getBody().getEntities();
            projects = entities.stream().map( e -> {
                Project project = new Project();
                project.setId(e.getProperty("ProjectID").getValue().toString());
                project.setName(e.getProperty("ProjectName").getValue().toString());
                project.setConfidential(e.getProperty("Confidential").getValue().toString().equals("Y"));
                return project;
            }).collect(Collectors.toList());

		} catch (URISyntaxException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Unable to retrieve projects list.", e);
            }
			throw new S4ServerException(e);
		}
		return projects;
	}

	public List<Workspace> getWorkspaces(final String projectId) {
        List<Workspace> workspaces;
        try {
            ODataEntitySetRequest<ClientEntitySet> request = this.client.getRetrieveRequestFactory().getEntitySetRequest(new URI(projectReadApiEndpoint + "/ProjectSet('"+ projectId+"')/WorkpackageSet"));
            request.addCustomHeader("apikey", configuration.getApiKey().orElseThrow(() -> new S4ServerException()));
            request.addCustomHeader("Accept", "application/xml");
            ODataRetrieveResponse<ClientEntitySet> response = request.execute();
            List<ClientEntity> entities = response.getBody().getEntities();
            workspaces = entities.stream().map( e -> {
                Workspace w = new Workspace();
                w.setId(e.getProperty("WorkPackageID").getValue().toString());
                w.setName(e.getProperty("WorkPackageName").getValue().toString());
                return w;
            }).collect(Collectors.toList());

        } catch (URISyntaxException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Unable to retrieve workspaces list.", e);
            }
            throw new S4ServerException(e);
        }
        return workspaces;
    }

    public List<WorkItem> getWorkItems(final String workspaceId) {
        List<WorkItem> workItems;
        try {
            ODataEntitySetRequest<ClientEntitySet> request = this.client.getRetrieveRequestFactory().getEntitySetRequest(new URI(projectReadApiEndpoint + "/WorkpackageSet('"+ workspaceId+"')/WorkItemSet"));
            request.addCustomHeader("apikey", configuration.getApiKey().orElseThrow(() -> new S4ServerException()));
            request.addCustomHeader("Accept", "application/xml");
            ODataRetrieveResponse<ClientEntitySet> response = request.execute();
            List<ClientEntity> entities = response.getBody().getEntities();
            workItems = entities.stream().map( e -> {
                WorkItem w = new WorkItem();
                w.setId(e.getProperty("Workitem").getValue().toString());
                w.setName(e.getProperty("Workitemname").getValue().toString());
                return w;
            }).collect(Collectors.toList());

        } catch (URISyntaxException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Unable to retrieve work items.", e);
            }
            throw new S4ServerException(e);
        }
        return workItems;
    }

    public String fetchCsrfToken() {
        String token;
        try {
            ODataRawRequest request = this.client.getRetrieveRequestFactory().getRawRequest(new URI(projectWriteApiEndpoint ));
            request.addCustomHeader("apikey", configuration.getApiKey().orElseThrow(() -> new S4ServerException()));
            request.addCustomHeader("Accept", "application/json");
            request.addCustomHeader("x-csrf-token", "fetch");
            ODataRawResponse response = request.execute();
            Optional<String> header = response.getHeader("x-csrf-token").stream().findFirst();
            token = header.get();
        } catch (Exception e) {
            throw new S4ServerException(e);
        }
        return token;
    }

	public Project getProjectById(final String projectId) {
	    // TODO
	    return new Project();
    }

    public Project addProject(final Project project) {
	    final String token = fetchCsrfToken();
	    // TODO use the token in the create action
	    return project;
    }
}
