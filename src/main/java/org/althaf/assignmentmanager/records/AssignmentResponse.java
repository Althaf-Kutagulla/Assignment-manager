package org.althaf.assignmentmanager.records;

import org.althaf.assignmentmanager.constants.AssignmentStatus;

public record AssignmentResponse(
        String id,
        String userId,
        String task,
        String admin,
        AssignmentStatus status
) {
}
