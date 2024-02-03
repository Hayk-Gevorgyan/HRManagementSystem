package org.company.management;

import org.company.hibernate.HibernateConfig;
import org.hibernate.Session;

import java.util.Scanner;


public class HR {
    private static final Session session = HibernateConfig.getSessionFactory().openSession();
    private static final Scanner scanner = new Scanner(System.in);
    public static Session getSession() {
        return session;
    }

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("""
                    Commands
                    1 - create
                    2 - read
                    3 - update
                    4 - delete
                    5 - exit""");
            String command = scanner.nextLine().trim();
            switch (command) {
                case "1" -> {
                    boolean exitOption = false;
                    while (!exitOption){
                        System.out.println("""
                                Options
                                1 - employee
                                2 - manager
                                3 - project
                                4 - department
                                5 - exit""");
                        String option = scanner.nextLine();
                        switch (option) {
                            case "1" -> EmployeeManager.createEmployee(session);
                            case "2" -> ManagerManager.createManager(session);
                            case "3" -> ProjectManager.createProject(session);
                            case "4" -> DepartmentManager.createDepartment(session);
                            case "5" -> exitOption = true;
                            default -> System.out.println("invalid option");
                        }
                    }
                }
                case "2" -> {
                    boolean exitOption = false;
                    while (!exitOption){
                        System.out.println("""
                                Options
                                1 - employee
                                2 - manager
                                3 - project
                                4 - department
                                5 - exit""");
                        String option = scanner.nextLine();
                        switch (option) {
                            case "1" -> EmployeeManager.listEmployees(session);
                            case "2" -> ManagerManager.listManagers(session);
                            case "3" -> ProjectManager.listProjects(session);
                            case "4" -> DepartmentManager.listDepartments(session);
                            case "5" -> exitOption = true;
                            default -> System.out.println("invalid option");
                        }
                    }
                }
                case "3" -> {
                    boolean exitOption = false;
                    while (!exitOption){
                        System.out.println("""
                                Options
                                1 - employee
                                2 - manager
                                3 - project
                                4 - department
                                5 - exit""");
                        String option = scanner.nextLine();
                        switch (option) {
                            case "1" -> EmployeeManager.updateEmployee(session);
                            case "2" -> ManagerManager.updateManager(session);
                            case "3" -> ProjectManager.updateProject(session);
                            case "4" -> DepartmentManager.updateDepartment(session);
                            case "5" -> exitOption = true;
                            default -> System.out.println("invalid option");
                        }
                    }
                }
                case "4" -> {
                    boolean exitOption = false;
                    while (!exitOption){
                        System.out.println("""
                                Options
                                1 - employee
                                2 - manager
                                3 - project
                                4 - department
                                5 - exit""");
                        String option = scanner.nextLine();
                        switch (option) {
                            case "1" -> EmployeeManager.deleteEmployee(session);
                            case "2" -> ManagerManager.deleteManager(session);
                            case "3" -> ProjectManager.deleteProject(session);
                            case "4" -> DepartmentManager.deleteDepartment(session);
                            case "5" -> exitOption = true;
                            default -> System.out.println("invalid option");
                        }
                    }
                }
                case "5" -> exit = true;
                default -> System.out.println("invalid command");
            }
        }
    }

}
