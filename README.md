# Coding Challenge
## What's Provided
A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped 
with data. The application contains information about all employees at a company. On application start-up, an in-memory 
Mongo database is bootstrapped with a serialized snapshot of the database. While the application runs, the data may be
accessed and mutated in the database without impacting the snapshot.

### How to Run
The application may be executed by running `spring bootRun`.  Application will start on port 8088 but can be changed to different port by modified the server.port in application.properties

### Access swagger:
http://localhost:8088/swagger-ui/index.html#/



### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```
The Employee has a JSON schema of:
```json
{
  "type":"Employee",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
          "type": "string"
    },
    "position": {
          "type": "string"
    },
    "department": {
          "type": "string"
    },
    "directReports": {
      "type": "array",
      "items" : "string"
    }
  }
}
```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

## What to Implement
Clone or download the repository, do not fork it.


### Task 1
Create a new type, ReportingStructure, that has two properties: employee and numberOfReports.

For the field "numberOfReports", this should equal the total number of reports under a given employee. The number of 
reports is determined to be the number of directReports for an employee and all of their distinct reports. For example, 
given the following employee structure:
```
                    John Lennon
                /               \
         Paul McCartney         Ringo Starr
                               /        \
                          Pete Best     George Harrison
```
The numberOfReports for employee John Lennon (employeeId: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4. 

This new type should have a new REST endpoint created for it. This new endpoint should accept an employeeId and return 
the fully filled out ReportingStructure for the specified employeeId. The values should be computed on the fly and will 
not be persisted.

Solution:
New Class: ReportingStructure 

* READ - Direct Report
    * HTTP Method: GET 
    * URL: localhost:8088/direct-report-by-emp-id/{id}
    * RESPONSE: List<ReportingStructure>

Start Up application and invoke the read direct Report API and enter in any employee id to find all the direct / indirect report from manager hierarchy (from sub-manager) 

For Example:  John Lennon(16a596ae-edd3-4847-99fe-c4518e82c86f) will have 4 employee(s) report.
              Paul McCartney(b7839309-3348-463b-a7e3-5de1c168beb3) will have 0 employee(s) report.
              Ringo Starr() will have 2 employee(s) report.
              

### Task 2
Create a new type, Compensation. A Compensation has the following fields: employee, salary, and effectiveDate. Create 
two new Compensation REST endpoints. One to create and one to read by employeeId. These should persist and query the 
Compensation from the persistence layer.

Solution:
New Class: Compensation 


Step 1: CREATE - Copensation
    * HTTP Method: POST 
    * URL: localhost:8080/compensation
    * PAYLOAD: Compensation
    * RESPONSE: Compensation(s)

Example of json body:
{
    "employeeId": "62c1084e-6e34-4630-93fd-9153afb65309",
    "salary": 100000,
    "effectiveDate": "2023-10-10"
}

Result: Note: employeeId is reference to the employeeId on the employee collection and upon retrieving the compensation and it will aggregate with employee collection
              to return the full employeeInfo.

{
    "employeeId": "62c1084e-6e34-4630-93fd-9153afb65309",
    "salary": 100000,
    "effectiveDate": "2023-10-10T00:00:00.000+00:00",
    "employeeInfo": []
}


* Step 2: READ - Compensation
    * HTTP Method: GET 
    * URL: localhost:8080/compensation-by-emp-id/{id}
    * RESPONSE: Employee

Note: Step 1 need to be complete first to add a compensation into the database before it can be retrieve in step 2.


Example: http://localhost:8080/compensation-by-emp-id/b7839309-3348-463b-a7e3-5de1c168beb4

Result:

[
    {
        "employeeId": "62c1084e-6e34-4630-93fd-9153afb65309",
        "salary": 100000,
        "effectiveDate": "2023-10-10T00:00:00.000+00:00",
        "employeeInfo": [
            {
                "employeeId": "62c1084e-6e34-4630-93fd-9153afb65309",
                "firstName": "Pete",
                "lastName": "Best",
                "position": "Developer II",
                "department": "Engineering"
            }
        ]
    }
]

## Delivery
Please upload your results to a publicly accessible Git repo. Free ones are provided by Github and Bitbucket.
A simple app showcasing how to use embedded Mongo inside a Spring Boot












