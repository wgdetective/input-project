package com.wgdetective.input.project.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth-test")
@PreAuthorize("hasAnyRole('READER', 'CREATOR')")
@RequiredArgsConstructor
public class TestAuthController {

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public String get() {
        return "Get result";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('delete')")
    public String delete() {
        return "Post result";
    }

}
