package com.xingyu.controller;

import com.xingyu.controller.dto.Credential;
import com.xingyu.repository.UserRepository;
import com.xingyu.service.SimpleAuthenticationProvider;
import com.xingyu.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/login")
@Tag(name = "Authentication", description = "Operations to manage Authentication")
public class AuthenticationController {
    private final SimpleAuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;

    public AuthenticationController(SimpleAuthenticationProvider authenticationProvider, UserRepository userRepository) {
        this.authenticationProvider = authenticationProvider;
        this.userRepository = userRepository;
    }

    @PostMapping
    @Operation(summary = "Login", description = "handleAuthenticationToken")
    public ResponseEntity<String> login(@Valid @RequestBody Credential credential) {
        if (authenticationProvider.authenticate(credential).isAuthenticated()) {
            return ResponseEntity.ok(JwtUtils.generateToken(userRepository.findByUsername(credential.getUsername())));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
