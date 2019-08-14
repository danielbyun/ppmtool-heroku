package org.danielbyun.ppmtool.service;

import lombok.extern.slf4j.Slf4j;
import org.danielbyun.ppmtool.model.User;
import org.danielbyun.ppmtool.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // extract user
        User user = userRepository.findByUsername(username);

        if (user == null) new UsernameNotFoundException("User not found");

        return user;
    }

    @Transactional
    public User loadUserById(Long id) {
        User user = userRepository.getById(id);

        if (user == null) new UsernameNotFoundException("User not found");

        return user;
    }
}
