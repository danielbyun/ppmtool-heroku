package org.danielbyun.ppmtool.controller;

import lombok.extern.slf4j.Slf4j;
import org.danielbyun.ppmtool.model.Project;
import org.danielbyun.ppmtool.service.MapValidationErrorService;
import org.danielbyun.ppmtool.service.ProjectService;
import org.danielbyun.ppmtool.service.ProjectTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectTaskService projectTaskService;
    private final MapValidationErrorService mapValidationErrorService;

    public ProjectController(ProjectService projectService, ProjectTaskService projectTaskService,
                             MapValidationErrorService mapValidationErrorService) {
        this.projectService = projectService;
        this.projectTaskService = projectTaskService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result,
                                              Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if (errorMap != null) {
            return errorMap;
        }

        log.info("new project: " + project.toString());
        projectService.saveOrUpdate(project, principal.getName());
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {
        Project project = projectService.findByProjectIdentifier(projectId, principal.getName());

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal) {
        return projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId, Principal principal) {
        projectService.deleteProjectByIdentifier(projectId, principal.getName());

        return new ResponseEntity<>("Project with ID: '" + projectId + "' was deleted.", HttpStatus.OK);
    }
}
