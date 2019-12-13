package io.zipcoder.persistenceapp.controller;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<Iterable<Employee>> getAllEmployees() {
        return new ResponseEntity<Iterable<Employee>>(employeeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<Iterable<Employee>> getDirectReports(@RequestBody Employee manager){
        return new ResponseEntity<Iterable<Employee>>(employeeService.getDirectReports(manager), HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<Employee>(employeeService.getOne(id), HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<Iterable<Employee>> getReportingHierarchy(@RequestBody Employee employee){
        return new ResponseEntity<Iterable<Employee>>(employeeService.getReportingHierarchy(employee), HttpStatus.OK);
    }

    @GetMapping("/employees")
    // FINDS ALL EMPLOYEES WITHOUT MANAGERS
    public ResponseEntity<Iterable<Employee>> getLostPuppies(){
        return new ResponseEntity<Iterable<Employee>>(employeeService.getLostPuppies(), HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<Iterable<Employee>> getDepartmentRoster(@RequestBody Department department){
        return new ResponseEntity<Iterable<Employee>>(employeeService.getDepartmentRoster(department), HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeService.update(employee), HttpStatus.OK);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateManager(@RequestBody Employee employee, Employee manager){
        return new ResponseEntity<Employee>(employeeService.updateManager(employee, manager), HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Employee> destroyEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return new ResponseEntity<Employee>(HttpStatus.OK);
    }

}
