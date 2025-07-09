package org.example.jobportalapp.myEnum;


import java.util.Arrays;

public enum RoleType {
    ADMIN("Admin"),
    EMPLOYER("Employer"),
    JOB_SEEKER("Job Seeker");

    private final String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Tìm RoleType từ tên hiển thị (ví dụ: "Admin") — dùng cho form nhập từ client.
     */
    public static RoleType fromDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(role -> role.displayName.equalsIgnoreCase(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role display name: " + displayName));
    }
}

