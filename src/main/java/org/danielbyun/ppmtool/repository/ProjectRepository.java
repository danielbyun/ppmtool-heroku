package org.danielbyun.ppmtool.repository;

import org.danielbyun.ppmtool.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByProjectIdentifier(String projectIdentifier);

    Iterable<Project> findAllByProjectLeader(String username);
}
