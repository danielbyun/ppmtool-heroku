package org.danielbyun.ppmtool.service;

import lombok.extern.slf4j.Slf4j;
import org.danielbyun.ppmtool.exception.ProjectIDException;
import org.danielbyun.ppmtool.exception.ProjectNotFoundException;
import org.danielbyun.ppmtool.model.Backlog;
import org.danielbyun.ppmtool.model.Project;
import org.danielbyun.ppmtool.model.User;
import org.danielbyun.ppmtool.repository.BacklogRepository;
import org.danielbyun.ppmtool.repository.ProjectRepository;
import org.danielbyun.ppmtool.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, BacklogRepository backlogRepository,
                              UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Project saveOrUpdate(Project project, String username) {
        String identifier = project.getProjectIdentifier().toUpperCase();

        if (project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
                throw new ProjectNotFoundException("Project not found in your account.");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier() + "' cannot " +
                        "be updated because it doesn't exist.");
            }
        }

        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(identifier);

            // on save, the ID will be null.
            // on update the ID will not be null
            if (project.getId() == null) {
                // create backlog on save only
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(identifier);
            } else if (project.getId() != null) { // updating
                project.setBacklog(backlogRepository.findByProjectIdentifier(identifier));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIDException("Project ID: " + project.getProjectIdentifier().toUpperCase() + " already " +
                    "exists");
        }
    }

    @Override
    public Project findByProjectIdentifier(String projectIdentifier, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier);

        if (project == null) {
            throw new ProjectIDException("Project ID: " + projectIdentifier + " does not exist");
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    @Override
    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    @Override
    public void deleteProjectByIdentifier(String projectIdentifier, String username) {
        projectRepository.delete(findByProjectIdentifier(projectIdentifier, username));
    }
}
