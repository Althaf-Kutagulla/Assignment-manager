package org.althaf.assignmentmanager.repository;

import org.althaf.assignmentmanager.entity.UserImpl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserImpl,String> {
    Optional<UserImpl> findByUserId(String username);

    boolean existsByUserId(String s);

    List<UserImpl> findByRole(String string);
}
