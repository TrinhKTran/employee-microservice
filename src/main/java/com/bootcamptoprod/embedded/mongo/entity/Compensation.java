package com.bootcamptoprod.embedded.mongo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Compensation {

    private String employeeId;
    private int salary;
    private Date effectiveDate;
    private List<Employee> employeeInfo = new ArrayList<Employee>();
}
