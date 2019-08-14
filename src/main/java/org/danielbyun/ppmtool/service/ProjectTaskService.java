package org.danielbyun.ppmtool.service;

import org.danielbyun.ppmtool.model.ProjectTask;

public interface ProjectTaskService {
    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username);
    Iterable<ProjectTask> findBacklogById(String backlog_id, String username);
    ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username);
    ProjectTask updateByProjectSequence(ProjectTask updatedPTask, String backlog_id, String pt_id, String username);
    void deletePTByProjectSequence(String backlog_id, String pt_id, String username);
}
