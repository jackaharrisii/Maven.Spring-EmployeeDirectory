package io.zipcoder.persistenceapp.services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Iterable<Department> getAll(){return departmentRepository.findAll();}

    public Department getOne(Long id) { return departmentRepository.findOne(id);}

    public Department create(Department department){
        return departmentRepository.save(department);
    }

    public Department update(Department department){
        return departmentRepository.save(department);
    }

    public Department updateName(Department department, String name){
        department.setDepartmentName(name);
        return departmentRepository.save(department);
    }

    public Department updateManager(Department department, Employee manager){
        department.setDepartmentManager(manager);
        return departmentRepository.save(department);
    }

    public Boolean delete(Long id){
        departmentRepository.delete(id);
        return true;
    }

}
