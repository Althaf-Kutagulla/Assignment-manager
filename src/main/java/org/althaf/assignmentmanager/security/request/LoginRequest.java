package org.althaf.assignmentmanager.security.request;

public record LoginRequest(
        String userId,
        String password
) {
}
