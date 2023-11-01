package com.wgdetective.input.project.auth.model;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.wgdetective.input.project.auth.model.Permission.CREATE;
import static com.wgdetective.input.project.auth.model.Permission.DELETE;
import static com.wgdetective.input.project.auth.model.Permission.READ;
import static com.wgdetective.input.project.auth.model.Permission.UPDATE;

@RequiredArgsConstructor
@Getter
public enum Role {

    USER(Collections.emptySet()), MEAL_ALL(Set.of(CREATE,
            READ,
            UPDATE,
            DELETE)), MEAL_READ(Set.of(READ));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
