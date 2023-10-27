package com.bootcamptoprod.embedded.mongo.service.impl;

import com.bootcamptoprod.embedded.mongo.entity.Compensation;
import com.bootcamptoprod.embedded.mongo.entity.Employee;
import com.bootcamptoprod.embedded.mongo.entity.ReportingStructure;
import com.bootcamptoprod.embedded.mongo.repository.CompensationRepository;
import com.bootcamptoprod.embedded.mongo.repository.EmployeeRepository;
import com.bootcamptoprod.embedded.mongo.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final MongoTemplate mongoTemplate;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompensationRepository compensationRepository;
    public EmployeeServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);
        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Retrieving employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating employee compensation[{}]", compensation);

        compensationRepository.insert(compensation);
        return compensation;
    }
    public List<ReportingStructure> performGraphLookupWithEmpIdMatch(String empid) {
        LOG.info("Retrieving Reporting Structure details for employee id[{}]", empid);

       Aggregation aggregation = Aggregation.newAggregation(
               //TODO Use MVEL to create rules for these query
                Aggregation.graphLookup("employee")
                        .startWith("$directReports.employeeId")
                        .connectFrom("directReports.employeeId")
                        .connectTo("employeeId")
                        .as("linearEmployeeReports"),// $graphLookup stage
               Aggregation.match(Criteria.where("employeeId").is(empid)),
               Aggregation.project("employeeId", "firstName", "lastName", "position", "department", "linearEmployeeReports")
                       .andExpression("size('$linearEmployeeReports')").as("numberOfReports")

       );

        AggregationResults<ReportingStructure> results = mongoTemplate.aggregate(aggregation, "employee", ReportingStructure.class);
        return results.getMappedResults();
    }

    public List<Compensation> performAggregationForComp(String empid) {
        //TODO Use MVEL to create rules for these query
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("employee", "employeeId", "employeeId", "employeeInfo"),
                Aggregation.match(Criteria.where("employeeId").is(empid))
                // Add more aggregation stages if needed
        );

        AggregationResults<Compensation> results = mongoTemplate.aggregate(aggregation, "compensation", Compensation.class);
        return results.getMappedResults();
    }
}
