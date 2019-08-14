package org.danielbyun.ppmtool.repository;

import org.danielbyun.ppmtool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User getById(Long id);
}
