package org.althaf.assignmentmanager.repository;

import org.althaf.assignmentmanager.entity.Assignment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends MongoRepository<Assignment,String> {

    List<Assignment> findByUserId(String userId);
    List<Assignment> findByAdmin(String string);
}
