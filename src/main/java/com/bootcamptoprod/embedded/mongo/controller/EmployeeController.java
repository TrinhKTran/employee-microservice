package com.bootcamptoprod.embedded.mongo.controller;

import com.bootcamptoprod.embedded.mongo.entity.Compensation;
import com.bootcamptoprod.embedded.mongo.entity.Employee;
import com.bootcamptoprod.embedded.mongo.entity.ReportingStructure;
import com.bootcamptoprod.embedded.mongo.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    /*@GetMapping("/version")
    public String getMongoDBVersion() {
        return employeeService.getMongoDBVersion();
    }*/
    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    @GetMapping("/direct-report-by-emp-id/{id}")
    public List<ReportingStructure> directReportGraphLookupByEmpId(@PathVariable String id) {
        LOG.debug("Received employee direct report request for id [{}]", id);

        return employeeService.performGraphLookupWithEmpIdMatch(id);
    }

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation creation request for [{}]", compensation);

        return employeeService.create(compensation);
    }

    @GetMapping("/compensation-by-emp-id/{id}")
    public List<Compensation> getAllByNestedObject(@PathVariable String id) {
        LOG.debug("Retrieve employee compensation request for id [{}]", id);

        return employeeService.performAggregationForComp(id);
    }

}
