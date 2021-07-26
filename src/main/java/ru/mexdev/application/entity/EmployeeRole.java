package ru.mexdev.application.entity;

import java.util.UUID;

public enum EmployeeRole {
    FOUNDER,
    ADMIN,
    MANAGER,
    EMPLOYEE;

    private UUID uuid;

    EmployeeRole() {
        uuid = UUID.randomUUID();
    }
}
