package com.wgdetective.input.project.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth-test")
@PreAuthorize("hasAnyRole('MEAL_ALL', 'MEAL_READ')")
public class TestAuthController {

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public String get() {
        return "Get result";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DELETE')")
    public String delete() {
        return "Delete result";
    }

}
