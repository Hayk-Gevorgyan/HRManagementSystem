package org.company.management;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.company.entities.Department;
import org.company.entities.Manager;
import org.company.util.Location;
import org.company.util.Validation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DepartmentManager {
    public static void createDepartment(Session session) {
        Transaction transaction = null;
        String departmentName = Validation.getDepartmentName();
        Location location = Validation.getLocation();
        Department department = new Department(departmentName, location);
        try {
            transaction = session.beginTransaction();
            session.persist(department);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("creation failed");
        }
    }
    public static List<Long> listDepartments(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Department> criteriaQuery = builder.createQuery(Department.class);
        Root<Department> departmentRoot = criteriaQuery.from(Department.class);

        criteriaQuery.multiselect(
                departmentRoot.get("departmentId"),
                departmentRoot.get("departmentName")
        );

        List<Department> results = session.createQuery(criteriaQuery).getResultList();
        List<Long> departmentIds = new ArrayList<>();
        System.out.println("ID\t|\tName");

        for (Department department : results) {
            departmentIds.add(department.getDepartmentId());
            System.out.println(department.getDepartmentId() + "\t|\t" + department.getDepartmentName());
        }

        return departmentIds;
    }
    public static void updateDepartment(Session session) {
        Transaction transaction = null;
        Long departmentId = Validation.getDepartmentId();
        if (departmentId == null) {
            return;
        }
        Department department = session.get(Department.class, departmentId);

        String departmentName = Validation.getDepartmentName();
        Location location = Validation.getLocation();
        department.getDepartmentHead().setDepartment(null);
        session.merge(department.getDepartmentHead());
        Long managerId = Validation.getManagerId();
        if (departmentName != null){
            department.setDepartmentName(departmentName);
        }
        if (location != null){
            department.setLocation(location);
        }
        if (managerId != null){
            Manager departmentHead = session.get(Manager.class, managerId);
            department.setDepartmentHead(departmentHead);
        }
        try {
            transaction = session.beginTransaction();
            session.merge(department);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("update failed");
        }
    }
    public static void deleteDepartment(Session session) {
        Transaction transaction = null;
        Long departmentId = Validation.getDepartmentId();
        if (departmentId == null) {
            return;
        }
        Department department = session.get(Department.class, departmentId);

        try {
            transaction = session.beginTransaction();
            session.remove(department);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null){
                transaction.rollback();
            }
            System.out.println("deletion failed");
        }
    }
}
