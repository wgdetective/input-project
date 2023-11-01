package com.wgdetective.input.project.auth.dto;

import com.wgdetective.input.project.auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    private String name;

    private String email;

    private String password;

    private Role role;

}
