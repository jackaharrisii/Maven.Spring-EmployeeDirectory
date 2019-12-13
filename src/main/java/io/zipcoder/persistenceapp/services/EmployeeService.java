package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.DepartmentRepository;
import io.zipcoder.persistenceapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    private DepartmentService departmentService;

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

    // THIS SHOULD ALSO RETURN AN EMPTY ArrayList IF (Employee manager) IS NOT ACTUALLY A MANAGER
    public Iterable<Employee> getDirectAndIndirectReports(Employee manager){
        ArrayList<Employee> allReports = new ArrayList<>();
        for (Employee employee : getAll()){
            if (getReportingHierarchy(employee).contains(manager)){
                allReports.add(employee);
            }
        }
        return allReports;
    }

    public Iterable<Employee> getDepartmentRoster(Department department){
        return getDirectAndIndirectReports(department.getDepartmentManager());
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
        try {
            employeeRepository.delete(id);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Boolean purgeDirectAndIndirectReports(Employee manager){
        try {
            Iterable<Employee> purgeList = getDirectAndIndirectReports(manager);
            massPurge(purgeList);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Boolean purgeDepartment(Department department){
        try{
            Iterable<Employee> purgeList = getDepartmentRoster(department);
            massPurge(purgeList);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void massPurge(Iterable<Employee> employees) throws Exception{
        for (Employee employee : employees){
            delete(employee.getEmployeeNumber());
        }
    }

    public Boolean removeDirectReportsAndPromoteSubordinates(Employee manager) {
        try {
            ArrayList<Employee> grandReports = new ArrayList<>();
            for (Employee employee : getDirectReports(manager)) {
                for (Employee grandkid : getDirectReports(employee)) {
                    grandReports.add(grandkid);
                }
            }
            for (Employee employee : getDirectReports(manager)) {
                delete(employee.getEmployeeNumber());
            }
            for (Employee grandkid : grandReports) {
                grandkid.setManager(manager);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Employee getEmployeeInfo(Long id){
        Employee employee = getOne(id);
        System.out.format("****************************************" +
                "Name: %s %s\n" +
                "Title: %s\n" +
                "Phone: %s\n" +
                "Hire Date: %s\n" +
                "Department: %s" +
                "Manager: %s" +
                "****************************************",
                employee.getFirstName(), employee.getLastName(),
                employee.getTitle(),
                employee.getPhoneNumber(),
                employee.getHireDate().toString(),
                departmentService.getOne(employee.getDepartmentNumber()),
                employee.getManager());
        return employee;
    }

    public Boolean mergeDepartments(Department gainingDept, Department losingDept){
        try{
            for(Employee employee : getDepartmentRoster(losingDept)){
                employee.setDepartmentNumber(gainingDept.getDepartmentNumber());
            }
            losingDept.getDepartmentManager().setManager(gainingDept.getDepartmentManager());
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
