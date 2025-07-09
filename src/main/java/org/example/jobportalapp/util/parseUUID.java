package org.example.jobportalapp.util;

import java.util.UUID;

public final class parseUUID {
    private parseUUID() {
        // Private constructor to prevent instantiation
    }
    public static UUID parseUUID(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            throw new IllegalArgumentException("UUID string cannot be null or empty");
        }
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID string: " + uuid, e);
        }
    }
}
