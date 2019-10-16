package com.queryinterface.sapioscpworkshop;

import java.util.Objects;

public class Project {

    private String name;
    private String id;
    private ProjectStage projectStage = ProjectStage.In_Planning;
    private String organizationId;
    private String costCenter;
    private ProjectCategory category = ProjectCategory.Internal;
    private boolean confidential = false;

    public ProjectStage getProjectStage() {
        return projectStage;
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

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", id=" + id +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id &&
                Objects.equals(name, project.name) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    public void setConfidential(boolean confidential) {
        this.confidential = confidential;
    }

    public boolean isConfidential() {
        return this.confidential;
    }
}
