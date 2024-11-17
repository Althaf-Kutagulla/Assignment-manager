package org.althaf.assignmentmanager.records;

import org.althaf.assignmentmanager.constants.Role;

public record UserReponse(
         String id,
         String firstName,
         String lastName,
         String userId,
         Role role) {
}
