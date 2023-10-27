package com.bootcamptoprod.embedded.mongo.service;

import com.bootcamptoprod.embedded.mongo.entity.Compensation;
import com.bootcamptoprod.embedded.mongo.entity.Employee;
import com.bootcamptoprod.embedded.mongo.entity.ReportingStructure;

import java.util.List;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    public List<ReportingStructure> performGraphLookupWithEmpIdMatch(String empid);
    Employee update(Employee employee);
    Compensation create(Compensation compensation);
    public List<Compensation> performAggregationForComp(String empid);
}
