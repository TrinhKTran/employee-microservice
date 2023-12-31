package com.bootcamptoprod.embedded.mongo.repository;

import com.bootcamptoprod.embedded.mongo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    void deleteById(String id);
    Optional<User> findById(String id);
    Boolean existsById(String id);
}
