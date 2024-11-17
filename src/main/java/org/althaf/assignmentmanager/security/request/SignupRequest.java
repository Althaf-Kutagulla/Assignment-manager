package org.althaf.assignmentmanager.security.request;

import org.althaf.assignmentmanager.constants.Role;

public record SignupRequest(
        String firstName,
        String lastName,
        String userId,
        String password,
        Role role
) {

}
