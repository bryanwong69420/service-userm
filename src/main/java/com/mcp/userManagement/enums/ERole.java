package com.mcp.userManagement.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ERole {
    ROLE_USER("ROLE_USER", "USER"),
    ROLE_ADMIN("ROLE_ADMIN", "ADMIN");

    private final String key;
    private final String desc;

    ERole(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static Optional<ERole> getRole(String value) {
        return Arrays.stream(ERole.values())
                .filter(e -> e.key.equalsIgnoreCase(value.replaceAll("\\s+", "")))
                .findFirst();
    }
}
