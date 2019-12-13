package io.zipcoder.persistenceapp.controller;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public ResponseEntity<Iterable<Department>> getAllDepartments(){
        return new ResponseEntity<Iterable<Department>>(departmentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id){
        return new ResponseEntity<Department>(departmentService.getOne(id), HttpStatus.OK);
    }

    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department){
        return new ResponseEntity<Department>(departmentService.create(department), HttpStatus.CREATED);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department){
        return new ResponseEntity<Department>(departmentService.update(department), HttpStatus.OK);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartmentManager(@RequestBody Department department, Employee manager){
        return new ResponseEntity<Department>(departmentService.updateManager(department, manager), HttpStatus.OK);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartmentName(@RequestBody Department department, String name){
        return new ResponseEntity<Department>(departmentService.updateName(department, name), HttpStatus.OK);
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Department> destroyDepartment(@PathVariable Long id){
        departmentService.delete(id);
        return new ResponseEntity<Department>(HttpStatus.OK);
    }

}
