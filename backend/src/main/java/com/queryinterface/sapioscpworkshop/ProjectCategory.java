package com.queryinterface.sapioscpworkshop;

public enum ProjectCategory {
    Internal("I"),
    Customer("C");

    private String id;
    ProjectCategory(final String id) {
        this.id = id;
    }

    public static ProjectCategory fromId(final String id) {
        if (id.equals("C")) {
            return Customer;
        }
        return Internal;
    }
}
