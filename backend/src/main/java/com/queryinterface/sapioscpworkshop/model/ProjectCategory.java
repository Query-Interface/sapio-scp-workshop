package com.queryinterface.sapioscpworkshop.model;

public enum ProjectCategory {
    Internal("I"),
    Customer("C");

    private String id;
    ProjectCategory(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public static ProjectCategory fromId(final String id) {
        if (id.equals("C")) {
            return Customer;
        }
        return Internal;
    }
}
