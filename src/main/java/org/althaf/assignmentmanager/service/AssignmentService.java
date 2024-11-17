package org.althaf.assignmentmanager.service;

import lombok.RequiredArgsConstructor;
import org.althaf.assignmentmanager.constants.AssignmentStatus;
import org.althaf.assignmentmanager.entity.Assignment;
import org.althaf.assignmentmanager.entity.UserImpl;
import org.althaf.assignmentmanager.exception.AssignmentNotFound;
import org.althaf.assignmentmanager.exception.MessageResponse;
import org.althaf.assignmentmanager.mappers.AssignmentMapper;
import org.althaf.assignmentmanager.records.AssignmentRequest;
import org.althaf.assignmentmanager.records.AssignmentResponse;
import org.althaf.assignmentmanager.repository.AssignmentRepository;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    public AssignmentResponse createAssignment(UserDetails userDetails, AssignmentRequest assignmentRequest) {
        Assignment assignment = assignmentMapper.toAssignment(assignmentRequest);
        assignment.setStatus(AssignmentStatus.PENDING);
        Assignment savedAssignment = assignmentRepository.save(assignment);
        return assignmentMapper.toAssignmentResponse(savedAssignment);
    }

    public AssignmentResponse getAssignmentById(String assignmentId) {
        List<Assignment> allAsssignments = assignmentRepository.findAll();
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()->{return new AssignmentNotFound(String.format("Assignment Not found with id :%s",assignmentId));
                }
        );

        return assignmentMapper.toAssignmentResponse(assignment);
    }


    public List<AssignmentResponse> getAssignments(UserDetails userDetails) {
        UserImpl user = (UserImpl) userDetails;
        String role = user.getRole().toString();

       if(role.equals("USER")){
            List<Assignment> assignments = assignmentRepository.findByUserId(user.getUserId());
            List<AssignmentResponse> assignmentResponses =assignments.stream()
                    .map(assignmentMapper::toAssignmentResponse)
                    .toList();
            return assignmentResponses;
       }

       if(role.equals("ADMIN")){
           List<Assignment> assignments = assignmentRepository.findByAdmin(user.getUserId());
           List<AssignmentResponse> assignmentResponses =assignments.stream()
                   .map(assignmentMapper::toAssignmentResponse)
                   .toList();
           return assignmentResponses;
       }
        return new ArrayList<>();
    }


    public AssignmentResponse createAssignment(AssignmentRequest assignmentRequest) {
        Assignment assignment = assignmentMapper.toAssignment(assignmentRequest);
        assignment.setStatus(AssignmentStatus.PENDING);
        Assignment savedAssignment = assignmentRepository.save(assignment);
        return assignmentMapper.toAssignmentResponse(savedAssignment);
    }

    public AssignmentResponse acceptAssignment(String assignmentId){
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()->{return new AssignmentNotFound(String.format("Assignment Not found with id :%s",assignmentId));
                }
        );
        assignment.setStatus(AssignmentStatus.ACCEPTED);
        return assignmentMapper.toAssignmentResponse(assignmentRepository.save(assignment));
    }

    public AssignmentResponse rejectAssignment(String assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()->{return new AssignmentNotFound(String.format("Assignment Not found with id :%s",assignmentId));
                }
        );
        assignment.setStatus(AssignmentStatus.REJECTED);
        return assignmentMapper.toAssignmentResponse(assignmentRepository.save(assignment));
    }
}
