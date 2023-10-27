package com.bootcamptoprod.embedded.mongo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportingStructure {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private int numberOfReports;
    private List<Employee> linearEmployeeReports;
}
