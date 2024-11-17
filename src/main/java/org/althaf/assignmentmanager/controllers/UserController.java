package org.althaf.assignmentmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.althaf.assignmentmanager.entity.UserImpl;
import org.althaf.assignmentmanager.exception.MessageResponse;
import org.althaf.assignmentmanager.records.AssignmentRequest;
import org.althaf.assignmentmanager.records.AssignmentResponse;
import org.althaf.assignmentmanager.records.UserReponse;
import org.althaf.assignmentmanager.service.AssignmentService;
import org.althaf.assignmentmanager.service.UserDetailsServiceImpl;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/users")
public class UserController {

    private final AssignmentService assignmentService;
    private final UserDetailsServiceImpl userDetailsService;
    @PostMapping("/upload")
    public ResponseEntity<AssignmentResponse> uploadAssignment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AssignmentRequest assignmentRequest){
        UserImpl user = (UserImpl) userDetails;
        if(!user.getRole().toString().equals("USER")){
            throw new MessageResponse(String.format("You need to have USER role to dom this activity"));
        }
        return new ResponseEntity<>(assignmentService.createAssignment(assignmentRequest), HttpStatus.CREATED);
    }
    @GetMapping("/admins")
        public ResponseEntity<List<UserReponse>> getAllAdmins(@AuthenticationPrincipal UserDetails userDetails){
        UserImpl user = (UserImpl) userDetails;
        if(!user.getRole().toString().equals("USER")){
            throw new MessageResponse(String.format("You need to have USER role to dom this activity"));
        }
            return new ResponseEntity<>(userDetailsService.getAllAdmins(),HttpStatus.OK);
    }

}
