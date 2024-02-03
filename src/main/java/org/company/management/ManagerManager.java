package org.company.management;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.company.entities.Department;
import org.company.entities.Employee;
import org.company.entities.Manager;
import org.company.util.ManagementLevel;
import org.company.util.Validation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManagerManager {

    public static void createManager(Session session) {
        Transaction transaction = null;
        String name = Validation.getEmployeeName();
        String email = Validation.getEmployeeEmail();
        String phone = Validation.getEmployeePhone();
        LocalDate hireDate = Validation.getDate();
        String jobTitle = Validation.getJobTitle();
        Long departmentId = Validation.getDepartmentId();
        Department department = session.get(Department.class, departmentId);
        Manager manager = new Manager(name, email, phone, hireDate, jobTitle, department);
        try {
            transaction = session.beginTransaction();
            session.persist(manager);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("creation failed");
        }
    }
    public static List<Long> listManagers(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Manager> criteriaQuery = builder.createQuery(Manager.class);
        Root<Manager> managerRoot = criteriaQuery.from(Manager.class);

        criteriaQuery.multiselect(
                managerRoot.get("employeeId"),
                managerRoot.get("employeeName")
        );

        List<Manager> results = session.createQuery(criteriaQuery).getResultList();
        List<Long> managerIds = new ArrayList<>();
        System.out.println("ID\t|\tName");

        for (Manager manager : results) {
            managerIds.add(manager.getEmployeeID());
            System.out.println(manager.getEmployeeID() + "\t|\t" + manager.getEmployeeName());
        }

        return managerIds;
    }
    public static void updateManager(Session session) {
        Transaction transaction = null;
        Long managerId = Validation.getManagerId();
        if (managerId == null) {
            return;
        }
        Manager manager = session.get(Manager.class, managerId);

        String email = Validation.getEmployeeEmail();
        String phone = Validation.getEmployeePhone();
        String jobTitle = Validation.getJobTitle();
        ManagementLevel managementLevel = Validation.getManagementLevel();
        Long managedDepartmentId = Validation.getDepartmentId();
        Department managedDepartment = session.get(Department.class, managedDepartmentId);

        if (email != null) {
            manager.setEmployeeEmail(email);
        }
        if (phone != null) {
            manager.setEmployeePhone(phone);
        }
        if (jobTitle != null) {
            manager.setEmployeeJobTitle(jobTitle);
        }
        if (managementLevel != null) {
            manager.setManagementLevel(managementLevel);
        }
        if (managedDepartment != null) {
            managedDepartment.getDepartmentHead().setManagedDepartment(null);
            manager.setManagedDepartment(managedDepartment);
        }
        try {
            transaction = session.beginTransaction();
            session.merge(manager);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("update failed");
        }
    }
    public static void deleteManager(Session session) {
        Transaction transaction = null;
        Long managerId = Validation.getManagerId();
        if (managerId == null) {
            return;
        }
        Manager manager = session.get(Manager.class, managerId);

        try {
            transaction = session.beginTransaction();
            session.remove(manager);
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
