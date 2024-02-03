package org.company.management;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.company.entities.Department;
import org.company.entities.Manager;
import org.company.entities.Project;
import org.company.util.Location;
import org.company.util.Validation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager {
    public static void createProject(Session session) {
        Transaction transaction = null;
        String name = Validation.getProjectName();
        LocalDate startDate = Validation.getDate();
        LocalDate endDate = Validation.getProjectEndDate(startDate);
        BigDecimal budget = Validation.getProjectBudget();

        Project project = new Project(name, startDate, endDate, budget);

        try {
            transaction = session.beginTransaction();
            session.persist(project);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null){
                transaction.rollback();
            }
            System.out.println("creation failed");
        }
    }
    public static List<Long> listProjects(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = builder.createQuery(Project.class);
        Root<Project> projectRoot = criteriaQuery.from(Project.class);

        criteriaQuery.multiselect(
                projectRoot.get("projectId"),
                projectRoot.get("projectName")
        );

        List<Project> results = session.createQuery(criteriaQuery).getResultList();
        List<Long> projectIds = new ArrayList<>();
        System.out.println("ID\t|\tName");

        for (Project project : results) {
            projectIds.add(project.getProjectId());
            System.out.println(project.getProjectId() + "\t|\t" + project.getProjectName());
        }

        return projectIds;
    }
    public static void updateProject(Session session) {
        Transaction transaction = null;
        Long projectId = Validation.getProjectId();
        if (projectId == null) {
            return;
        }
        Project project = session.get(Project.class, projectId);

        String name = Validation.getProjectName();
        LocalDate endDate = Validation.getProjectEndDate(project.getStartDate());
        BigDecimal budget = Validation.getProjectBudget();
        if (name != null){
            project.setProjectName(name);
        }
        if (endDate != null){
            project.setEndDate(endDate);
        }
        if (budget != null){
            project.setBudget(budget);
        }
        try {
            transaction = session.beginTransaction();
            session.merge(project);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("update failed");
        }
    }
    public static void deleteProject(Session session) {
        Transaction transaction = null;

        Long projectId = Validation.getProjectId();

        Project project = session.get(Project.class, projectId);

        try {
            transaction = session.beginTransaction();
            session.remove(project);
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
