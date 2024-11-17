package org.althaf.assignmentmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.althaf.assignmentmanager.entity.UserImpl;
import org.althaf.assignmentmanager.exception.MessageResponse;
import org.althaf.assignmentmanager.records.AssignmentResponse;
import org.althaf.assignmentmanager.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AssignmentService assignmentService;

    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentResponse>> getAssignments(@AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(assignmentService.getAssignments(userDetails), HttpStatus.OK);
    }

    @PostMapping("/assignments/{id}/accept")
    public ResponseEntity<AssignmentResponse> acceptAssignment(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("id") String assignmentId){
        UserImpl user = (UserImpl) userDetails;
        if(!assignmentService.getAssignmentById(assignmentId).admin().equals(user.getUserId())){
            throw new MessageResponse("Admin role required to perform this activity");
        }
        return new ResponseEntity<>(assignmentService.acceptAssignment(assignmentId),HttpStatus.OK);
    }

    @PostMapping("/assignments/{id}/reject")
    public ResponseEntity<AssignmentResponse> rejectAssignment(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("id") String assignmentId){
        UserImpl user = (UserImpl) userDetails;
        if(!assignmentService.getAssignmentById(assignmentId).admin().equals(user.getUserId())){
            throw new MessageResponse("Admin role required to perform this activity");
        }
        return new ResponseEntity<>(assignmentService.rejectAssignment(assignmentId),HttpStatus.OK);
    }

}
