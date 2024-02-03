package org.company.entities;

import jakarta.persistence.*;
import org.company.util.ManagementLevel;

import java.time.LocalDate;

@Entity
@Table(name = "managers")
public class Manager extends Employee{
    @OneToOne(mappedBy = "department_head")
    private Department managedDepartment;
    @Column(name = "management_level")
    @Enumerated(value = EnumType.STRING)
    private ManagementLevel managementLevel;

    public Manager() {
    }

    public Manager(Employee employee) {
        this(employee.employeeName, employee.employeeEmail, employee.employeePhone, employee.hireDate, "Manager",employee.department);
    }

    public Manager(String employeeName, String employeeEmail, String employeePhone, LocalDate hireDate, String employeeJobTitle, Department department) {
        super(employeeName, employeeEmail, employeePhone, hireDate, employeeJobTitle, department);
        this.managementLevel = ManagementLevel.FIRST_LINE;
    }

    public Department getManagedDepartment() {
        return managedDepartment;
    }

    public void setManagedDepartment(Department managedDepartment) {
        this.managedDepartment = managedDepartment;
    }

    public ManagementLevel getManagementLevel() {
        return managementLevel;
    }

    public void setManagementLevel(ManagementLevel managementLevel) {
        this.managementLevel = managementLevel;
    }
}
