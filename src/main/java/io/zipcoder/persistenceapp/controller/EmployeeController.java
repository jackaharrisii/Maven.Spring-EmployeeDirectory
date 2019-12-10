package io.zipcoder.persistenceapp.controller;

import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {
    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/Employees")
    public ResponseEntity<Iterable<Employee>> index() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/Employees/{id}")
    public ResponseEntity<Employee> show(@PathVariable Long id) {
        return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
    }

    @PostMapping("/Employees")
    public ResponseEntity<Employee> create(@RequestBody Employee Employee) {
        return new ResponseEntity<>(service.create(Employee), HttpStatus.CREATED);
    }

    @PutMapping("/Employees/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee Employee) {
        return new ResponseEntity<>(service.update(id, Employee), HttpStatus.OK);
    }

    @DeleteMapping("/Employees/{id}")
    public ResponseEntity<Boolean> destroy(@PathVariable Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

}
