package org.company.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @SequenceGenerator(name = "employee_sequence", sequenceName = "employee_seq", allocationSize = 1)
    @Column(name = "employee_id")
    protected Long employeeId;
    @Column(name = "employee_name")
    protected String employeeName;
    @Column(name = "employee_email")
    protected String employeeEmail;
    @Column(name = "employee_phone")
    protected String employeePhone;
    @Column(name = "hire_date")
    protected LocalDate hireDate;
    @Column(name = "employee_job_title")
    protected String employeeJobTitle;
    @ManyToOne
    protected Department department;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    protected Set<Project> projects = new HashSet<>();

    public Employee() {
    }

    public Employee(String employeeName, String employeeEmail, String employeePhone, LocalDate hireDate, String employeeJobTitle) {
        this(employeeName, employeeEmail, employeePhone, hireDate, employeeJobTitle, null);
    }

    public Employee(String employeeName, String employeeEmail, String employeePhone, LocalDate hireDate, String employeeJobTitle, Department department) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeePhone = employeePhone;
        this.hireDate = hireDate;
        this.employeeJobTitle = employeeJobTitle;
        this.department = department;
    }

    public Manager promote() {
        return new Manager(this);
    }

    public Long getEmployeeID() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmployeeJobTitle() {
        return employeeJobTitle;
    }

    public void setEmployeeJobTitle(String employeeJobTitle) {
        this.employeeJobTitle = employeeJobTitle;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void assignProjectsToEmployee(Collection<Project> projects) {
        this.projects.addAll(projects);
    }

    public void assignProjectToEmployee(Project project) {
        this.projects.add(project);
    }

    public void reassignProjectsFromEmployee(Collection<Project> projects) {
        this.projects.removeAll(projects);
    }

    public void reassignProjectFromEmployee(Project project) {
        this.projects.remove(project);
    }
}
