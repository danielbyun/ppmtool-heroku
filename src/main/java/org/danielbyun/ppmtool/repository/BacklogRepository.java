package org.danielbyun.ppmtool.repository;

import org.danielbyun.ppmtool.model.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogRepository extends JpaRepository<Backlog, Long> {
    Backlog findByProjectIdentifier(String identifier);
}
