package org.company.util;

import org.company.management.*;
import org.hibernate.engine.jdbc.WrappedNClob;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validation {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String EXIT = "-e";
    private static final String NOW = "NOW";
    private static final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern emailPattern = Pattern.compile(emailRegex);
    private static final String phoneRegex = "^\\d{8}$";
    private static final Pattern phonePattern = Pattern.compile(phoneRegex);
    private static final String ARMENIAN_CALLING_CODE = "+374";



    public static String getEmployeeName() {
        boolean exit = false;
        String inputString;
        String name = null;
        while (!exit) {
            System.out.print("input employee name or -e to exit");
            inputString = scanner.nextLine();

            if(inputString.equals(EXIT)) {
                exit = true;
            }
            if (inputString.length() < 30) {
                name = inputString;
                exit = true;
            }
        }
        return name;
    }
    @Nullable
    public static String getEmployeeEmail() {
        boolean exit = false;
        String inputString;
        String email = null;
        while (!exit) {
            System.out.println("input a valid email address or -e to exit");
            inputString = scanner.nextLine();
            if (emailPattern.matcher(inputString).matches()) {
                email = inputString;
                exit = true;
            } else if (inputString.equals(EXIT)) {
                exit = true;
            } else {
                System.out.println("invalid email address");
            }
        }
        return email;
    }
    @Nullable
    public static String getEmployeePhone() {
        boolean exit = false;
        String inputString;
        String phone = null;
        while (!exit) {
            System.out.print(ARMENIAN_CALLING_CODE);
            System.out.println("input a valid phone number (8 digits) or -e to exit");
            inputString = scanner.nextLine();
            if (phonePattern.matcher(inputString).matches()) {
                phone = inputString;
                exit = true;
            } else if (inputString.equals(EXIT)) {
                exit = true;
            } else {
                System.out.println("invalid phone number");
            }
        }
        return phone;
    }
    @Nullable
    public static LocalDate getDate() {
        boolean exit = false;
        LocalDate date = null;

        while (!exit) {
            System.out.print("Enter a date (yyyy-MM-dd): ");
            System.out.println("Enter a date before now , NOW or -e to exit");
            String dateString = scanner.nextLine();
            if (dateString.equals(NOW)) {
                date = LocalDate.now();
                exit = true;
                continue;
            } else if (dateString.equals(EXIT)) {
                exit = true;
                continue;
            }
            try{
                date = LocalDate.parse(dateString);
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                date = null;
            }
            if (date != null && date.isAfter(LocalDate.now())) {
                date = null;
            }
        }
        return date;
    }
    @Nullable
    public static LocalDate getProjectEndDate(LocalDate startDate) {
        boolean exit = false;
        LocalDate endDate = null;

        while (!exit) {
            System.out.print("Enter a date (yyyy-MM-dd): ");
            System.out.println("Enter a date after start date  or -e to exit");

            String dateString = scanner.nextLine();
            if (dateString.equals(EXIT)) {
                exit = true;
                continue;
            }
            try {
                endDate = LocalDate.parse(dateString);
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
            if (endDate != null && endDate.isAfter(startDate) {
                exit = true;
            }
        }
        return endDate;
    }
    @Nullable
    public static String getJobTitle() {
        System.out.println("Enter employees job title or -e to exit");
        String jobTitle = scanner.nextLine();
        if (jobTitle.equals(EXIT)) {
            jobTitle = null;
        }
        return jobTitle;
    }
    @Nullable
    public static String getDepartmentName() {
        System.out.println("Enter department name or -e to exit");
        String departmentName = scanner.nextLine();
        if (departmentName.equals(EXIT)) {
            departmentName = null;
        }
        return departmentName;
    }
    @Nullable
    public static Location getLocation() {
        System.out.println("Enter city or -e to exit");
        String city = scanner.nextLine();
        if (city.equals(EXIT)){
            return null;
        }
        System.out.println("Enter country or -e to exit");
        String country = scanner.nextLine();
        if (country.equals(EXIT)){
            return null;
        }
        return new Location(city,country);
    }
    @Nullable
    public static Long getDepartmentId() {
        List<Long> departmentIds = DepartmentManager.listDepartments(HR.getSession());
        Long departmentId = null;
        boolean set = false;
        while (!set) {
            System.out.println("choose department by id or -e to exit");
            String inputString = scanner.nextLine();
            if (inputString.equals(EXIT)) {
                break;
            }
            try {
                departmentId = Long.valueOf(inputString);
            } catch (NumberFormatException e) {
                System.out.println("not a valid number");
                continue;
            }
            if (departmentIds.contains(departmentId)) {
                set = true;
            } else {
                System.out.println("no such department id");
            }
        }
        return departmentId;
    }
    @Nullable
    public static Long getManagerId() {
        List<Long> managerIds = ManagerManager.listManagers(HR.getSession());
        Long managerId = null;
        boolean set = false;
        while (!set) {
            System.out.println("choose manager by id or -e to exit");
            String inputString = scanner.nextLine();
            if (inputString.equals(EXIT)) {
                break;
            }
            try {
                managerId = Long.valueOf(inputString);
            } catch (NumberFormatException e) {
                System.out.println("not a valid number");
                continue;
            }
            if (managerIds.contains(managerId)) {
                set = true;
            } else {
                System.out.println("no such manager id");
            }
        }
        return managerId;
    }
    @Nullable
    public static Long getEmployeeId() {
        List<Long> employeeIds = EmployeeManager.listEmployees(HR.getSession());
        Long employeeId = null;
        boolean set = false;
        while (!set) {
            System.out.println("choose employee by id or -e to exit");
            String inputString = scanner.nextLine();
            if (inputString.equals(EXIT)) {
                break;
            }
            try {
                employeeId = Long.valueOf(inputString);
            } catch (NumberFormatException e) {
                System.out.println("not a valid number");
                continue;
            }
            if (employeeIds.contains(employeeId)) {
                set = true;
            } else {
                System.out.println("no such employee id");
            }
        }
        return employeeId;
    }
    @Nullable
    public static ManagementLevel getManagementLevel() {
        boolean exit = false;
        ManagementLevel managementLevel = null;
        while (!exit) {
            System.out.println("getting management level\n" +
                    "enter 1 for top level, 2 for mid level, 3 for first line or -e to exit");
            String command = scanner.nextLine().trim();
            if (command.equals(EXIT)) {
                exit = true;
                continue;
            }
            switch (command) {
                case "1" -> managementLevel = ManagementLevel.TOP_LEVEL;
                case "2" -> managementLevel = ManagementLevel.MID_LEVEL;
                case "3" -> managementLevel = ManagementLevel.FIRST_LINE;
                default -> System.out.println("invalid command");
            }
        }
        return managementLevel;
    }
    @Nullable
    public static String getProjectName() {
        System.out.println("enter project name or -e to exit");
        String projectName = scanner.nextLine();
        if (projectName.equals(EXIT)) {
            projectName = null;
        }
        return projectName;
    }
    @Nullable
    public static BigDecimal getProjectBudget() {
        BigDecimal budget = null;
        boolean exit = false;

        while (!exit) {
            System.out.println("enter project budget or -e to exit");
            String inputString = scanner.nextLine();
            if (inputString.equals(EXIT)) {
                exit = true;
            }
            try {
                budget = BigDecimal.valueOf(Double.parseDouble(inputString));
                exit = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return budget;
    }
    @Nullable
    public static Long getProjectId() {
        List<Long> projectIds = ProjectManager.listProjects(HR.getSession());
        Long projectId = null;
        boolean set = false;
        while (!set) {
            System.out.println("choose project by id or -e to exit");
            String inputString = scanner.nextLine();
            if (inputString.equals(EXIT)) {
                break;
            }
            try {
                projectId = Long.valueOf(inputString);
            } catch (NumberFormatException e) {
                System.out.println("not a valid number");
                continue;
            }
            if (projectIds.contains(projectId)) {
                set = true;
            } else {
                System.out.println("no such employee id");
            }
        }
        return projectId;
    }
}
