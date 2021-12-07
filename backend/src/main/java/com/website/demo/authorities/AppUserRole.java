package com.website.demo.authorities;

import com.google.common.collect.Sets;
import com.website.demo.authorities.AppUserPermission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.website.demo.authorities.AppUserPermission.*;

public enum AppUserRole {
    ADMIN(Sets.newHashSet(
            VISIT_READ,
            VISIT_WRITE,
            PRESCRIPTION_READ,
            PRESCRIPTION_WRITE)),
    DOCTOR(Sets.newHashSet(
            VISIT_READ,
            VISIT_WRITE,
            PRESCRIPTION_READ,
            PRESCRIPTION_WRITE)),
    RECEPTION_WORKER(Sets.newHashSet()),
    PATIENT(Sets.newHashSet());

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
