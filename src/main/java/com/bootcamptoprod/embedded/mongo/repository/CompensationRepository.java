package com.bootcamptoprod.embedded.mongo.repository;

import com.bootcamptoprod.embedded.mongo.entity.Compensation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CompensationRepository extends MongoRepository <Compensation, String>{
    @Query("{'employee.employeeId': ?0}")
    List<Compensation> findCompByEmployeeId(final String employeeId);
}
