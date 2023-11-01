package com.wgdetective.input.project.auth.controller;

import java.io.IOException;

import com.wgdetective.input.project.auth.dto.AuthenticationResponse;
import com.wgdetective.input.project.auth.dto.SignInRequest;
import com.wgdetective.input.project.auth.dto.SignUpRequest;
import com.wgdetective.input.project.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponse> signUp(
            @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(service.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(
            @RequestBody SignInRequest request) {
        return ResponseEntity.ok(service.signIn(request));
    }

    @PostMapping("/refresh")
    public void refresh(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        service.refresh(request, response);
    }

}
