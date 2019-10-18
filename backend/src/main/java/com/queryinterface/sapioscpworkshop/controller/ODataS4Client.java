package com.queryinterface.sapioscpworkshop.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityCreateRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataRawRequest;
import org.apache.olingo.client.api.communication.response.ODataEntityCreateResponse;
import org.apache.olingo.client.api.communication.response.ODataRawResponse;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.queryinterface.sapioscpworkshop.DateHelper;
import com.queryinterface.sapioscpworkshop.config.Configuration;
import com.queryinterface.sapioscpworkshop.exception.S4ServerException;
import com.queryinterface.sapioscpworkshop.model.Project;
import com.queryinterface.sapioscpworkshop.model.ProjectCategory;
import com.queryinterface.sapioscpworkshop.model.ProjectStage;
import com.queryinterface.sapioscpworkshop.model.WorkItem;
import com.queryinterface.sapioscpworkshop.model.Workspace;

@Service
public class ODataS4Client {

	private static final String ENTITYSET_PROJECT = "ProjectSet";
	private static final String ENTITYSET_WORK_ITEM = "WorkItemSet";
	private static final String ENTITYSET_WORKPACKAGE = "WorkpackageSet";
	private static final String HEADER_ACCEPT = "Accept";
	private static final String HEADER_APIKEY = "apikey";
	private static final String HEADER_X_CSRF_TOKEN = "x-csrf-token";
	
	private static final String END_DATE = "EndDate";
	private static final String START_DATE = "StartDate";
	private static final String CUSTOMER = "Customer";
	private static final String CURRENCY = "Currency";
	private static final String PROJ_MANAGER_EXT_ID = "ProjManagerExtId";
	private static final String PROFIT_CENTER = "ProfitCenter";
	private static final String ORG_ID = "OrgId";
	private static final String COST_CENTER = "CostCenter";
	private static final String PROJECT_STAGE = "ProjectStage";
	private static final String PROJECT_CATEGORY = "ProjectCategory";
	private static final String PROJECT_NAME = "ProjectName";
	private static final String CONFIDENTIAL = "Confidential";
	private static final String PROJECT_ID = "ProjectID";
		
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
		ODataEntitySetRequest<ClientEntitySet> request = this.client.getRetrieveRequestFactory().getEntitySetRequest(
				this.client.newURIBuilder(projectReadApiEndpoint).appendEntitySetSegment(ENTITYSET_PROJECT).build());
		request.addCustomHeader(HEADER_APIKEY, configuration.getApiKey().orElseThrow(() -> new S4ServerException()));
		request.addCustomHeader(HEADER_ACCEPT, "application/xml");
		ODataRetrieveResponse<ClientEntitySet> response = request.execute();
		List<ClientEntity> entities = response.getBody().getEntities();
        projects = entities.stream().map( e -> {
            Project project = new Project();
            project.setId(getProperty(e, PROJECT_ID));
            project.setName(getProperty(e, PROJECT_NAME));
            project.setConfidential(getProperty(e, CONFIDENTIAL).equals("Y"));
            project.setProjectStage(ProjectStage.fromId(getProperty(e, PROJECT_STAGE)));
            project.setStartDate(DateHelper.unserializeDate(getProperty(e, START_DATE)));
            project.setEndDate(DateHelper.unserializeDate(getProperty(e, END_DATE)));
            project.setCostCenter(getProperty(e, COST_CENTER));
            project.setCategory(ProjectCategory.fromId(getProperty(e, PROJECT_CATEGORY)));
            project.setCurrency(getProperty(e, CURRENCY));
            project.setCustomerId(getProperty(e, CUSTOMER));
            project.setProfitCenter(getProperty(e, PROFIT_CENTER));
            project.setOrganizationId(getProperty(e, ORG_ID));
            project.setProjectManagerId(getProperty(e, PROJ_MANAGER_EXT_ID));
            
            return project;
        }).collect(Collectors.toList());
		return projects;
	}

	private String getProperty(final ClientEntity entity, final String propertyName) {
		ClientProperty property = entity.getProperty(propertyName);
		if (property != null && !property.hasNullValue()) {
			return property.getValue().toString();
		} else {
			return null;
		}
	}
	
	public Project addProject(final Project project) {
	    final String token = fetchCsrfToken();
        ClientEntity projectEntity = this.client.getObjectFactory().newEntity(new FullQualifiedName("CPD_SC_EXTERNAL_SERVICES_SRV", "Project"));
        // add entity properties
        projectEntity.getProperties().add(createProperty(PROJECT_ID, project.getId()));
        projectEntity.getProperties().add(createProperty(CONFIDENTIAL, project.isConfidential() ? "Y" :"N"));
        projectEntity.getProperties().add(createProperty(PROJECT_NAME, project.getName()));
        projectEntity.getProperties().add(createProperty(PROJECT_CATEGORY, project.getCategory().getId()));
        projectEntity.getProperties().add(createProperty(PROJECT_STAGE, project.getProjectStage().getId()));
        projectEntity.getProperties().add(createProperty(COST_CENTER, project.getCostCenter()));
        projectEntity.getProperties().add(createProperty(ORG_ID, project.getOrganizationId()));
        projectEntity.getProperties().add(createProperty(PROFIT_CENTER, project.getProfitCenter()));
        projectEntity.getProperties().add(createProperty(PROJ_MANAGER_EXT_ID, project.getProjectManagerId()));
        projectEntity.getProperties().add(createProperty(CURRENCY, project.getCurrency()));
        projectEntity.getProperties().add(createProperty(CUSTOMER, project.getCustomerId()));
        projectEntity.getProperties().add(createProperty(START_DATE, DateHelper.serializeDate(project.getStartDate())));
        projectEntity.getProperties().add(createProperty(END_DATE, DateHelper.serializeDate(project.getEndDate())));
        ODataEntityCreateRequest<ClientEntity> request = this.client.getCUDRequestFactory().getEntityCreateRequest(
                this.client.newURIBuilder(projectWriteApiEndpoint).appendEntitySetSegment(ENTITYSET_PROJECT).build(),
                projectEntity);
        // add csrf token in the create action
        request.addCustomHeader(HEADER_X_CSRF_TOKEN, token);
        ODataEntityCreateResponse<ClientEntity> response = request.execute();
        ClientEntity createdEntity = response.getBody();

        return project;
    }

	public List<Workspace> getWorkspaces(final String projectId) {
        List<Workspace> workspaces;
        ODataEntitySetRequest<ClientEntitySet> request = this.client.getRetrieveRequestFactory().getEntitySetRequest(
        		this.client.newURIBuilder(projectReadApiEndpoint).appendEntitySetSegment(ENTITYSET_PROJECT).appendKeySegment(projectId).appendEntitySetSegment(ENTITYSET_WORKPACKAGE).build());
        		//new URI(projectReadApiEndpoint + "/ProjectSet('"+ projectId+"')/WorkpackageSet"));
        request.addCustomHeader(HEADER_APIKEY, configuration.getApiKey().orElseThrow(() -> new S4ServerException()));
        request.addCustomHeader(HEADER_ACCEPT, "application/json");
        ODataRetrieveResponse<ClientEntitySet> response = request.execute();
        List<ClientEntity> entities = response.getBody().getEntities();
        workspaces = entities.stream().map( e -> {
            Workspace w = new Workspace();
            w.setId(e.getProperty("WorkPackageID").getValue().toString());
            w.setName(e.getProperty("WorkPackageName").getValue().toString());
            return w;
        }).collect(Collectors.toList());
        return workspaces;
    }

    public List<WorkItem> getWorkItems(final String workspaceId) {
        List<WorkItem> workItems;
        ODataEntitySetRequest<ClientEntitySet> request = this.client.getRetrieveRequestFactory().getEntitySetRequest(
        		//new URI(projectReadApiEndpoint + "/WorkpackageSet('"+ workspaceId+"')/WorkItemSet"));
        		this.client.newURIBuilder(projectReadApiEndpoint).appendEntitySetSegment(ENTITYSET_WORKPACKAGE).appendKeySegment(workspaceId).appendEntitySetSegment(ENTITYSET_WORK_ITEM).build());
        request.addCustomHeader(HEADER_APIKEY, configuration.getApiKey().orElseThrow(() -> new S4ServerException()));
        request.addCustomHeader(HEADER_ACCEPT, "application/json");
        ODataRetrieveResponse<ClientEntitySet> response = request.execute();
        List<ClientEntity> entities = response.getBody().getEntities();
        workItems = entities.stream().map( e -> {
            WorkItem w = new WorkItem();
            w.setId(e.getProperty("Workitem").getValue().toString());
            w.setName(e.getProperty("Workitemname").getValue().toString());
            return w;
        }).collect(Collectors.toList());
        return workItems;
    }

    public String fetchCsrfToken() {
        String token;
        try {
            ODataRawRequest request = this.client.getRetrieveRequestFactory().getRawRequest(new URI(projectWriteApiEndpoint ));
            request.addCustomHeader(HEADER_APIKEY, configuration.getApiKey().orElseThrow(() -> new S4ServerException()));
            request.addCustomHeader(HEADER_ACCEPT, "application/json");
            request.addCustomHeader(HEADER_X_CSRF_TOKEN, "fetch");
            ODataRawResponse response = request.execute();
            Optional<String> header = response.getHeader(HEADER_X_CSRF_TOKEN).stream().findFirst();
            token = header.get();
        } catch (Exception e) {
        	if (logger.isErrorEnabled()) {
                logger.error("Unable to retrieve csrf token.", e);
            }
            throw new S4ServerException(e);
        }
        return token;
    }

	public Project getProjectById(final String projectId) {
	    // TODO
	    return new Project();
    }

    private ClientProperty createProperty(final String propId, final String propValue) {
	    return this.client.getObjectFactory().newPrimitiveProperty(propId,
                this.client.getObjectFactory().newPrimitiveValueBuilder().buildString(propValue));
    }
}
