package org.danielbyun.ppmtool.service;

import org.danielbyun.ppmtool.model.Project;

public interface ProjectService {
    Project saveOrUpdate(Project project, String username);
    Project findByProjectIdentifier(String projectIdentifier, String username);
    Iterable<Project> findAllProjects(String username);
    void deleteProjectByIdentifier(String projectIdentifier, String username);
}
