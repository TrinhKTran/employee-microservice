package com.bootcamptoprod.embedded.mongo.startup;

import com.bootcamptoprod.embedded.mongo.entity.Compensation;
import com.bootcamptoprod.embedded.mongo.entity.Employee;
import com.bootcamptoprod.embedded.mongo.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class InitializeData {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() throws IOException {
        System.out.println("Initializing data");

        ObjectMapper objectMapper = new ObjectMapper();
        List<User> persons = objectMapper.readValue(
                new ClassPathResource("/data/users.json").getFile(),
                new TypeReference<List<User>>() {
                });

        // Insert the data into the database
        mongoTemplate.insertAll(persons);

        System.out.println("Data initialization completed successfully for users");

        ObjectMapper objecEmptMapper = new ObjectMapper();
        List<Employee> employees = objecEmptMapper.readValue(
                new ClassPathResource("/data/employee_database.json").getFile(),
                new TypeReference<List<Employee>>() {
                });

        // Insert the data into the database
        mongoTemplate.insertAll(employees);
        System.out.println("Data initialization completed successfully for employees");

        ObjectMapper objecCompMapper = new ObjectMapper();
        List<Compensation> compensation = objecCompMapper.readValue(
                new ClassPathResource("/data/compensations_database.json").getFile(),
                new TypeReference<List<Compensation>>() {
                });

        // Insert the data into the database
        mongoTemplate.insertAll(compensation);
        System.out.println("Data initialization completed successfully for compensation");

        //Create index for unique record

    }
}
