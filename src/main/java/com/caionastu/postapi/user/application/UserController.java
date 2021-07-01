package com.caionastu.postapi.user.application;

import com.caionastu.postapi.commons.application.annotation.ApiPageable;
import com.caionastu.postapi.commons.application.response.ApiCollectionResponse;
import com.caionastu.postapi.user.application.request.CreateUserRequest;
import com.caionastu.postapi.user.application.request.UpdateUserRequest;
import com.caionastu.postapi.user.application.request.UserFilterRequest;
import com.caionastu.postapi.user.application.response.UserResponse;
import com.caionastu.postapi.user.domain.User;
import com.caionastu.postapi.user.exception.UserDeactivatedException;
import com.caionastu.postapi.user.exception.UserEmailAlreadyExistException;
import com.caionastu.postapi.user.repository.UserRepository;
import com.caionastu.postapi.user.repository.UserSpecification;
import com.caionastu.postapi.user.service.FindUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users")
@AllArgsConstructor
@Slf4j
@Api(value = "user-operations", tags = "User Operations")
public class UserController {

    private final UserRepository repository;
    private final FindUserService findUserService;

    @ApiPageable
    @GetMapping
    @ApiOperation(value = "Search users with filters")
    public ResponseEntity<ApiCollectionResponse<UserResponse>> findAll(@ApiIgnore Pageable pageable, UserFilterRequest filter) {
        log.info("Receiving request to find all users.");

        Page<UserResponse> users = repository.findAll(UserSpecification.from(filter), pageable)
                .map(UserResponse::from);

        ApiCollectionResponse<UserResponse> response = ApiCollectionResponse.from(users);
        log.info("Retrieving all users.");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Search user by id")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        log.info("Receiving request to find user by id: {}.", id);

        User user = findUserService.byId(id);

        UserResponse response = UserResponse.from(user);
        log.info("User retrieved by id with success.");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation(value = "Create a new user")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest request) {
        log.info("Receiving request to create a new user. Request: {}", request);
        boolean existsByEmail = repository.existsByEmail(request.getEmail());
        if (existsByEmail) {
            log.error("Already has a registered user with email: {}.", request.getEmail());
            throw new UserEmailAlreadyExistException(request.getEmail());
        }

        log.info("Creating new user.");
        User newUser = User.from(request);
        repository.save(newUser);
        log.info("User created with id: {}.", newUser.getId());

        UserResponse response = UserResponse.from(newUser);
        log.info("Create user request completed.");
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation("Update the user name")
    public ResponseEntity<Void> updateName(@PathVariable UUID id, @RequestBody @Valid UpdateUserRequest request) {
        log.info("Receiving request to update the name of the user with id: {}. Request: {}", id, request);

        User user = findUserService.byId(id);
        if (!user.isActive()) {
            log.error("User is deactivated.");
            throw new UserDeactivatedException(id);
        }

        log.info("Updating user name.");
        repository.updateName(id, request.getName());
        log.info("Name updated.");
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}/activate")
    @ApiOperation(value = "Activate a user")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        log.info("Receiving request to activate user by id: {}.", id);

        User user = findUserService.byId(id);
        if (user.isActive()) {
            log.error("User is already activated. Ignoring Request");
            return ResponseEntity.noContent().build();
        }

        log.info("Activating user.");
        user.activate();
        repository.save(user);
        log.info("User activated.");
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}/deactivate")
    @ApiOperation(value = "Deactivate a user")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        log.info("Receiving request to deactivate user by id: {}.", id);

        User user = findUserService.byId(id);
        if (!user.isActive()) {
            log.error("User is already deactivated. Ignoring Request");
            return ResponseEntity.noContent().build();
        }

        log.info("Deactivating user.");
        user.deactivate();
        repository.save(user);
        log.info("User deactivated.");
        return ResponseEntity.noContent().build();
    }

}
