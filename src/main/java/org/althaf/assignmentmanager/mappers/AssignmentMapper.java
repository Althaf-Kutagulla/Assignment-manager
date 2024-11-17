package org.althaf.assignmentmanager.mappers;

import org.althaf.assignmentmanager.entity.Assignment;
import org.althaf.assignmentmanager.records.AssignmentRequest;
import org.althaf.assignmentmanager.records.AssignmentResponse;
import org.springframework.stereotype.Service;

@Service
public class AssignmentMapper {
    public Assignment toAssignment(AssignmentRequest assignmentRequest) {
        return Assignment.builder()
                .userId(assignmentRequest.userId())
                .task(assignmentRequest.task())
                .admin(assignmentRequest.admin())
                .build();
    }

    public AssignmentResponse toAssignmentResponse(Assignment assignment) {
        return new AssignmentResponse(assignment.getId(),
                assignment.getUserId(),
                assignment.getTask(),
                assignment.getAdmin(),
                assignment.getStatus());
    }
}
