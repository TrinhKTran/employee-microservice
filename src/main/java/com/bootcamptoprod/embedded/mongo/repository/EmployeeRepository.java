package com.bootcamptoprod.embedded.mongo.repository;

import com.bootcamptoprod.embedded.mongo.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Employee findByEmployeeId(String employeeId);
}
