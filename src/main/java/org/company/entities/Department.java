package org.company.entities;

import jakarta.persistence.*;
import org.company.util.Location;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_sequence")
    @SequenceGenerator(name = "department_sequence", sequenceName = "department_seq", allocationSize = 1)
    @Column(name = "department_id")
    private Long departmentId;
    @Column(name = "department_name")
    private String departmentName;
    @Embedded
    @Column(name = "location")
    private Location location;
    @OneToOne
    @JoinColumn(name = "department_head")
    private Manager departmentHead;
    @OneToMany(mappedBy = "department")
    private final Set<Employee> employees = new HashSet<>();
    public Department() {}

    public Department(String departmentName, Location location) {
        this(departmentName,location,null);
    }

    public Department(String departmentName, Location location, Manager departmentHead) {
        this.departmentName = departmentName;
        this.location = location;
        this.departmentHead = departmentHead;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Manager getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(Manager departmentHead) {
        this.departmentHead = departmentHead;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void assignEmployeeToDepartment(Employee employee) {
        this.employees.add(employee);
    }

    public void assignEmployeesToDepartment(Collection<Employee> employees) {
        this.employees.addAll(employees);
    }

    public void reassignEmployeeFromDepartment(Employee employee) {
        this.employees.remove(employee);
    }

    public void reassignEmployeesFromDepartment(Collection<Employee> employees) {
        this.employees.removeAll(employees);
    }

}