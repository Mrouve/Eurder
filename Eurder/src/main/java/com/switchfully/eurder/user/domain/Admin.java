package com.switchfully.eurder.user.domain;

import java.time.LocalDate;
import java.util.UUID;

public class Admin extends User {

    private String adminPseudo;
    private LocalDate adminSince;
    private Role role;

    public Admin(String adminPseudo, LocalDate adminSince) {
        super();
        this.adminPseudo = adminPseudo;
        this.adminSince = adminSince;
    }

    @Override
    public Role setRole() {
        return this.role = Role.ADMIN;
    }
}
