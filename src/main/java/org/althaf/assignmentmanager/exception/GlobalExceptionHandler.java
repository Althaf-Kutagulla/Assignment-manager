package org.althaf.assignmentmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AssignmentNotFound.class})
    public ResponseEntity<ErrorResponse> handleAssignmentNotFoundException(AssignmentNotFound assignmentNotFound, WebRequest request){
        return new ResponseEntity<>(
                new ErrorResponse(request.getDescription(false).replace("uri=",""),assignmentNotFound.getMessage(),HttpStatus.NOT_FOUND.toString()),
                HttpStatus.NOT_FOUND
                );

    }


    @ExceptionHandler({MessageResponse.class})
    public ResponseEntity<ErrorResponse> handleMessageException(MessageResponse messageResponse,WebRequest request){
        return new ResponseEntity<>(
                new ErrorResponse(request.getDescription(false).replace("uri=",""),messageResponse.getMessage(),HttpStatus.BAD_REQUEST.toString()),
                HttpStatus.BAD_REQUEST
        );
    }
}
