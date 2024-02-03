package org.company.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "project_sequence")
    @SequenceGenerator(name = "project_sequence",sequenceName = "project_seq",allocationSize = 1)
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "budget")
    private BigDecimal budget;
    @ManyToMany(mappedBy = "projects")
    private final Set<Employee> employees = new HashSet<>();

    public Project() {
    }

    public Project(String projectName, LocalDate startDate, LocalDate endDate, BigDecimal budget) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void assignEmployeesToProject(Collection<Employee> employees) {
        this.employees.addAll(employees);
    }

    public void assignEmployeeToProject(Employee employee) {
        this.employees.add(employee);
    }

    public void reassignEmployeesFromProjects(Collection<Employee> employees) {
        this.employees.removeAll(employees);
    }

    public void reassignEmployeeFromProject(Employee employee) {
        this.employees.remove(employee);
    }
}
