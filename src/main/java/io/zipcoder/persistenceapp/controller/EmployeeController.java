package io.zipcoder.persistenceapp.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.services.DepartmentService;
import io.zipcoder.persistenceapp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/employees")
    public ResponseEntity<Iterable<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<Iterable<Employee>> getDirectReports(@RequestBody Employee manager){
        return new ResponseEntity<>(employeeService.getDirectReports(manager), HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getOne(id), HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<Iterable<Employee>> getReportingHierarchy(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.getReportingHierarchy(employee), HttpStatus.OK);
    }

    @GetMapping("/employees")
    // FINDS ALL EMPLOYEES WITHOUT MANAGERS
    public ResponseEntity<Iterable<Employee>> getLostPuppies(){
        return new ResponseEntity<>(employeeService.getLostPuppies(), HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<Iterable<Employee>> getDirectAndIndirectReports(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.getDirectAndIndirectReports(employee), HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<Iterable<Employee>> getDepartmentRoster(@RequestBody Department department){
        return new ResponseEntity<>(employeeService.getDepartmentRoster(department), HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<Employee> getEmployeeInfo(@PathVariable Long id){
        return new ResponseEntity<>(employeeService.getEmployeeInfo(id), HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.update(employee), HttpStatus.OK);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateManager(@RequestBody Employee employee, Employee manager){
        return new ResponseEntity<>(employeeService.updateManager(employee, manager), HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Employee> destroyEmployee(@PathVariable Long ...ids) {
        for(Long id : ids) {
            employeeService.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/employees")
    public ResponseEntity<Employee> purgeDepartmentStaff(Department department){
        employeeService.purgeDepartment(department);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/employees")
    public ResponseEntity<Employee> purgeDirectAndIndirectReports(Employee manager){
        employeeService.purgeDirectAndIndirectReports(manager);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/employees")
    public ResponseEntity<Employee> removeDirectReportsAndPromoteSubordinates(Employee manager){
        employeeService.removeDirectReportsAndPromoteSubordinates(manager);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/departments")
    @PutMapping("/employees")
    public ResponseEntity<Boolean> mergeDepartments(Department gainingDept, Department losingDept){
        employeeService.mergeDepartments(gainingDept, losingDept);
        departmentService.delete(losingDept.getDepartmentNumber());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
