package com.xingyu.controller;

import com.xingyu.domain.User;
import com.xingyu.group.OnUpdate;
import com.xingyu.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.validation.Valid;
import java.security.Principal;

import static com.xingyu.utils.ControllerUtil.currentUserVerification;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "users", description = "users operations")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    @Operation(summary = "Create a user", description = "Create a user")
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Retrieve a user details by id", description = "Retrieve a user details by id")
    public ResponseEntity<?> get(@PathVariable(value = "name") String name) throws AuthenticationException {
        currentUserVerification(name);
        return new ResponseEntity<>(userRepository.findByUsername(name), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Validated(OnUpdate.class)
    @Operation(summary = "Update a user details", description = "Update a user details")
    public ResponseEntity<User> put(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by id", description = "Delete a user by id")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
