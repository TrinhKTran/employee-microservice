package com.bootcamptoprod.embedded.mongo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Compensation {

    private String employeeId;
    private int salary;
    private Date effectiveDate;
    private List<Employee> employeeInfo = new ArrayList<Employee>();
}
