package com.caionastu.postapi.user.service;

import com.caionastu.postapi.user.domain.User;
import com.caionastu.postapi.user.exception.UserDeactivatedException;
import com.caionastu.postapi.user.exception.UserNotFoundException;
import com.caionastu.postapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindUserService {

    private final UserRepository repository;

    public User byId(UUID id) {
        log.info("Checking if user exists.");

        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}.", id);
                    throw new UserNotFoundException(id);
                });
    }

    public User byIdAndIsActive(UUID id) {
        User user = byId(id);
        checkIfIsActive(user);
        return user;
    }

    public void checkIfExistAndIsActive(UUID id) {
        User user = byId(id);
        checkIfIsActive(user);
    }

    private void checkIfIsActive(User user) {
        log.info("Checking if user is active.");

        if (!user.isActive()) {
            log.error("User with id {} is deactivated.", user.getId());
            throw new UserDeactivatedException(user.getId());
        }
    }

}
