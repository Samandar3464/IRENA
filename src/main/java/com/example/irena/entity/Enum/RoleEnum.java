package com.example.irena.entity.Enum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum RoleEnum {
    @Enumerated(EnumType.STRING)
    ADMIN,
    @Enumerated(EnumType.STRING)
    USER,
    @Enumerated(EnumType.STRING)
    SUPER_ADMIN
}
