package org.company.management;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.company.entities.Department;
import org.company.entities.Employee;
import org.company.util.Validation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    public static void createEmployee(Session session) {
        Transaction transaction = null;
        String name = Validation.getEmployeeName();
        String email = Validation.getEmployeeEmail();
        String phone = Validation.getEmployeePhone();
        LocalDate hireDate = Validation.getDate();
        String jobTitle = Validation.getJobTitle();
        Long departmentId = Validation.getDepartmentId();
        Department department = session.get(Department.class, departmentId);
        Employee employee = new Employee(name, email, phone, hireDate, jobTitle, department);
        try {
            transaction = session.beginTransaction();
            session.persist(employee);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("creation failed");
        }
    }
    public static List<Long> listEmployees(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        criteriaQuery.multiselect(
                employeeRoot.get("employeeId"),
                employeeRoot.get("employeeName"),
                employeeRoot.get("jobTitle")
        );

        List<Employee> results = session.createQuery(criteriaQuery).getResultList();
        List<Long> employeeIds = new ArrayList<>();
        System.out.println("ID\t|\tName\t|\tJob Title");

        for (Employee employee : results) {
            employeeIds.add(employee.getEmployeeID());
            System.out.println(employee.getEmployeeID() + "\t|\t" + employee.getEmployeeName() + "\t|\t" + employee.getEmployeeJobTitle());
        }

        return employeeIds;
    }
    public static void updateEmployee(Session session) {
        Transaction transaction = null;
        Long employeeId = Validation.getEmployeeId();
        if (employeeId == null) {
            return;
        }
        Employee employee = session.get(Employee.class, employeeId);

        String email = Validation.getEmployeeEmail();
        String phone = Validation.getEmployeePhone();
        String jobTitle = Validation.getJobTitle();

        if (email != null) {
            employee.setEmployeeEmail(email);
        }
        if (phone != null) {
            employee.setEmployeePhone(phone);
        }
        if (jobTitle != null) {
            employee.setEmployeeJobTitle(jobTitle);
        }
        try {
            transaction = session.beginTransaction();
            session.merge(employee);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("update failed");
        }
    }
    public static void deleteEmployee(Session session) {
        Transaction transaction = null;
        Long employeeId = Validation.getEmployeeId();
        if (employeeId == null) {
            return;
        }
        Employee employee = session.get(Employee.class, employeeId);

        try {
            transaction = session.beginTransaction();
            session.remove(employee);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("deletion failed");
        }
    }
}
