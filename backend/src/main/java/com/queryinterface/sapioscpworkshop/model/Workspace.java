package com.queryinterface.sapioscpworkshop.model;

import java.util.Date;

public class Workspace {
    private String id;
    private String name;
    private Date startDate;
    private Date endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
