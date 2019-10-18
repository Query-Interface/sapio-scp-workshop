package com.queryinterface.sapioscpworkshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.queryinterface.sapioscpworkshop.model.Project;
import com.queryinterface.sapioscpworkshop.model.WorkItem;
import com.queryinterface.sapioscpworkshop.model.Workspace;

@RestController
@RequestMapping("/api")
public class ProjectsController {

    private ODataS4Client client = new ODataS4Client();

    @RequestMapping(method = RequestMethod.GET, path = "/projects", produces = "application/json")
    public ResponseEntity<Iterable<Project>> getAllProjects() {
        return new ResponseEntity<>(client.getProjects(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/projects/{projectId}/workspaces", produces = "application/json")
    public ResponseEntity<Iterable<Workspace>> getWorkspaces(final @PathVariable String projectId) {
        return new ResponseEntity<>(client.getWorkspaces(projectId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/projects/{projectId}/workspaces/{workspaceId}/workitems", produces = "application/json")
    public ResponseEntity<Iterable<WorkItem>> getWorkItems(final @PathVariable String projectId, final @PathVariable String workspaceId) {
        return new ResponseEntity<>(client.getWorkItems(workspaceId), HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, path = "/projects", produces =  "application/json", consumes = "application/json")
    public ResponseEntity<Project> addProject(final @RequestBody Project project) {
        client.addProject(new Project());
        return new ResponseEntity<>(new Project(), HttpStatus.OK);
    }


}