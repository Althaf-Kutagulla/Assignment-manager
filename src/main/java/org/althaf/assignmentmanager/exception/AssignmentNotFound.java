package org.althaf.assignmentmanager.exception;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class AssignmentNotFound extends RuntimeException{
    public AssignmentNotFound(String message){
        super(message);
    }
}
