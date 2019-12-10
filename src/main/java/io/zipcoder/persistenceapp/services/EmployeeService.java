package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Iterable<Employee> getAll() {return repository.findAll();}
    
    public Employee getOne(Long id) {
        return repository.findOne(id);
    }

    public Employee create(Employee Employee) {
        return repository.save(Employee);
    }

    public Employee update(Long id, Employee newEmployeeData) {
        Employee originalEmployee = repository.findOne(id);
        originalEmployee.setFirstName(newEmployeeData.getFirstName());
        originalEmployee.setLastName(newEmployeeData.getLastName());
        return repository.save(originalEmployee);
    }

    public Boolean delete(Long id) {
        repository.delete(id);
        return true;
    }

}
