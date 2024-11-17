package org.althaf.assignmentmanager.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.althaf.assignmentmanager.constants.AssignmentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "assignment")
@Builder
@Getter
@Setter
public class Assignment {
    @Id
    private String id;
    private String userId;
    private String task;
    private String admin;
    private AssignmentStatus status;
}
