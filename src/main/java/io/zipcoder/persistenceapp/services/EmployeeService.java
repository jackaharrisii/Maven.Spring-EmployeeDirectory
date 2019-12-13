package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // UNNECESSARY DUE TO THE ABOVE
//    public EmployeeService(EmployeeRepository repository) {
//        this.repository = repository;
//    }

    public Iterable<Employee> getAll() {return employeeRepository.findAll();}

    public ArrayList<Employee> getDirectReports(Employee manager){
        ArrayList<Employee> directReports = new ArrayList<>();
        for (Employee employee : getAll()){
            if (employee.getManager().equals(manager)) directReports.add(employee);
        }
        return directReports;
    }

    public Employee getOne(Long id) {
        return employeeRepository.findOne(id);
    }

    public ArrayList<Employee> getReportingHierarchy(Employee employee){
        ArrayList<Employee> reportingHierarchy = new ArrayList<>();
        reportingHierarchy.add(employee);
        while (reportingHierarchy.get(reportingHierarchy.size()-1).getManager() != null){
            reportingHierarchy.add(reportingHierarchy.get(reportingHierarchy.size()-1).getManager());
        }
        return reportingHierarchy;
    }

    public ArrayList<Employee> getLostPuppies(){
        ArrayList<Employee> lostPuppies = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()){
            if (employee.getManager().equals(null) || employee.getManager().equals("")) lostPuppies.add(employee);
        }
        return lostPuppies;
    }

    public Iterable<Employee> getDepartmentRoster(Department department){
        ArrayList<Employee> departmentRoster = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()){
            if (getReportingHierarchy(employee).contains(department.getDepartmentManager())){
                departmentRoster.add(employee);
            }
        }
        return departmentRoster;
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee) {
//        Employee originalEmployee = repository.findOne(id);
//        originalEmployee.setFirstName(newEmployeeData.getFirstName());
//        originalEmployee.setLastName(newEmployeeData.getLastName());
        return employeeRepository.save(employee);
    }

    public Employee updateManager(Employee employee, Employee manager){
        employee.setManager(manager);
        return employee;
    }

    public Boolean delete(Long id) {
        employeeRepository.delete(id);
        return true;
    }

}
