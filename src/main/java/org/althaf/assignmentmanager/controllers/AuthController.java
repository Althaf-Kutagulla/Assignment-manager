package org.althaf.assignmentmanager.controllers;


import jakarta.validation.Valid;
import org.althaf.assignmentmanager.entity.UserImpl;
import org.althaf.assignmentmanager.exception.MessageResponse;
import org.althaf.assignmentmanager.records.LoginResponse;
import org.althaf.assignmentmanager.repository.UserRepository;
import org.althaf.assignmentmanager.security.jwt.JwtUtils;
import org.althaf.assignmentmanager.security.request.LoginRequest;
import org.althaf.assignmentmanager.security.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.userId(), loginRequest.password()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

//      set the authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        // Collect roles from the UserDetails
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Prepare the response body, now including the JWT token directly in the body
        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);

        // Return the response entity with the JWT token included in the response body
        return ResponseEntity.ok(response);
    }



    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUserId(signUpRequest.userId())) {
            throw new MessageResponse("Error: Username is already taken!");
        }

        // Create new user's account
        UserImpl user = new UserImpl(null, signUpRequest.firstName(),
                signUpRequest.lastName(),
                signUpRequest.userId(),
                encoder.encode(signUpRequest.password()),
                signUpRequest.role());
       UserImpl savedUser = userRepository.save(user);
        return new ResponseEntity<>(new MessageResponse(String.format("%s registered successfully.",savedUser.getRole().toString())),HttpStatus.CREATED);
    }
}
