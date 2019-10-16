package com.queryinterface.sapioscpworkshop;

import java.util.Arrays;
import java.util.Optional;

public enum ProjectStage {

    In_Planning("P001"),
    Contact_Preparation("P002"),
    In_Execution("P003"),
    Completed("P004"),
    Closed("P005");

    private String id;

    ProjectStage(final String id) {
        this.id = id;
    }

    public static ProjectStage fromId(final String id) {
        Optional<ProjectStage> stage = Arrays.stream(ProjectStage.values()).filter(ps -> ps.id.equals(id)).findAny();
        if (stage.isPresent()) {
            return stage.get();
        }
        return In_Planning;
    }
}
