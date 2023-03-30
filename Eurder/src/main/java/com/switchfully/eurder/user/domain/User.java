package com.switchfully.eurder.user.domain;

import java.util.UUID;

public abstract class User {
    private final UUID uuid;
    private Role role;

    public User() {
        this.uuid = UUID.randomUUID();
        this.role = setRole();
    }

    public UUID getUuid() {
        return uuid;
    }

    public Role getRole() {
        return role;
    }

    public Role setRole() {
        return this.role = Role.MEMBER;
    }
}
