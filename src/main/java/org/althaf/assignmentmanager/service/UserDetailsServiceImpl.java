package org.althaf.assignmentmanager.service;



import lombok.RequiredArgsConstructor;
import org.althaf.assignmentmanager.constants.Role;
import org.althaf.assignmentmanager.entity.UserImpl;
import org.althaf.assignmentmanager.exception.UserNotFoundException;
import org.althaf.assignmentmanager.records.UserReponse;
import org.althaf.assignmentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {



    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found with userId: " + userId));
    }


    public List<UserReponse> getAllAdmins(){
        List<UserImpl> users = userRepository.findByRole(Role.ADMIN.toString());
        return users.stream()
                .map(user -> {
                    return new UserReponse(user.getUserId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getUserId(),
                            user.getRole());
                }).toList();
    }




}
