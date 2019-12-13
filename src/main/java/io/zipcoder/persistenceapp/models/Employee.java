package io.zipcoder.persistenceapp.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long employeeNumber;

    private String firstName;
    private String lastName;
    private String title;
    private String phoneNumber;
    private String email;
    private Date hireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee manager;

    @ManyToOne //fetch stuff?
    private Integer departmentNumber;

    public Employee(){}

//    public Employee(Long employeeNumber, String firstName, String lastName, String title, String phoneNumber, String email, Date hireDate, Employee manager, Integer departmentNumber){
//        this.employeeNumber = employeeNumber;
//    }


    public Employee(String firstName, String lastName, String title, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.phoneNumber = phoneNumber;
        this.email = email;
//        this.hireDate = hireDate;
//        this.manager = manager;
//        this.departmentNumber = departmentNumber;
    }

    public Long getEmployeeNumber() {
        return employeeNumber;
    }

    // UNNECESSARY - THIS ID IS GENERATED
//    public void setEmployeeNumber(Integer employeeNumber) {
//        this.employeeNumber = employeeNumber;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Integer getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(Integer departmentNumber) {
        this.departmentNumber = departmentNumber;
    }
}
