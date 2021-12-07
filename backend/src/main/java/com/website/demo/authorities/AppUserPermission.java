package com.website.demo.authorities;

public enum AppUserPermission {
    VISIT_READ("visit:read"),
    VISIT_WRITE("visit:write"),
    PRESCRIPTION_READ("prescription:read"),
    PRESCRIPTION_WRITE("prescription:write");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }


}
