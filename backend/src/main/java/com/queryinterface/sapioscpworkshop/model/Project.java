package com.queryinterface.sapioscpworkshop.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Project {

    private String name;
    private String id;
    private ProjectStage projectStage = ProjectStage.In_Planning;
    private String organizationId = "SAP.iO Foundry";
    private String costCenter = "0123456789";
    private ProjectCategory category = ProjectCategory.Internal;
    private String customerId = "";
    private String currency = "EUR";
    private String projectManagerId = "";
    private String profitCenter = "SAP.iO Green Tech";
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public Project() {
    	startDate = LocalDateTime.now();
    	endDate = startDate.plusMonths(1);
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", projectStage=" + projectStage +
                ", organizationId='" + organizationId + '\'' +
                ", costCenter='" + costCenter + '\'' +
                ", category=" + category +
                ", customerId='" + customerId + '\'' +
                ", currency='" + currency + '\'' +
                ", projectManagerId='" + projectManagerId + '\'' +
                ", confidential=" + confidential +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return confidential == project.confidential &&
                Objects.equals(name, project.name) &&
                Objects.equals(id, project.id) &&
                projectStage == project.projectStage &&
                Objects.equals(organizationId, project.organizationId) &&
                Objects.equals(costCenter, project.costCenter) &&
                category == project.category &&
                Objects.equals(customerId, project.customerId) &&
                Objects.equals(currency, project.currency) &&
                Objects.equals(projectManagerId, project.projectManagerId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, id, projectStage, organizationId, costCenter, category, customerId, currency, projectManagerId, confidential);
    }

    public String getCustomerId() {

        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    private boolean confidential = false;

    public ProjectStage getProjectStage() {
        return projectStage;
    }

    public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}

	public void setProjectStage(ProjectStage projectStage) {
        this.projectStage = projectStage;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public ProjectCategory getCategory() {
        return category;
    }

    public void setCategory(ProjectCategory category) {
        this.category = category;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConfidential(boolean confidential) {
        this.confidential = confidential;
    }

    public boolean isConfidential() {
        return this.confidential;
    }
    
    public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
    
}
