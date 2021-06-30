package com.caionastu.postapi.user.service;

import com.caionastu.postapi.user.domain.User;
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
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}.", id);
                    throw new UserNotFoundException(id);
                });
    }

}
