package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GradeManagementSystem {
    public static void main(String[] args) throws SQLException {
        whatAction();
        connection.close();
    }
    public static String url = "jdbc:mariadb://localhost:3306";
    public static String dbUsername = "admin_user";
    public static String password = "admin";
    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url, dbUsername, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Scanner userInput = new Scanner(System.in);

    public static void whatAction() throws SQLException {

        System.out.println("What would you like to do?");
        System.out.println("");
        System.out.println("1 - Add a grade");
        System.out.println("2 - Delete a existing grade");
        System.out.println("3 - Update a existing grade");
        System.out.println("4 - Output all grades of one class with the average grade");
        System.out.println("5 - Output your report card");
        int whatNow = userInput.nextInt();
        switch (whatNow) {
            case 1 -> addGrade(url, dbUsername, password);
            case 2 -> deleteGrade(url, dbUsername, password);
            case 3 -> updateGrade(url, dbUsername, password);
            case 4 -> gradesOfOneClass(url, dbUsername, password);
            case 5 -> reportCard(url, dbUsername, password);
        }
    }

    private static void addGrade(String url, String dbUsername, String password) throws SQLException {
        System.out.println("");
        System.out.println("Which grade did you achieve?");
        System.out.println("Please note that only grades with 0.25 space are allowed.");
        System.out.println("For example: 4.5, 4.75, 5");
        float grade = userInput.nextFloat();
        System.out.println("");
        System.out.println("On which date did you achieve this grade? (YYYY-MM-DD)");
        System.out.println("For example: \"2022-03-10\"");
        userInput.nextLine();
        String dateString = userInput.nextLine();


        System.out.println("");
        System.out.println("Now for which class did you achieve this grade?");
        System.out.println("1 - Math");
        System.out.println("2 - English");
        System.out.println("3 - PE");
        System.out.println("4 - Science");
        System.out.println("5 - History");
        int subjectID = userInput.nextInt();
        System.out.println("");

        try (Connection connection = DriverManager.getConnection(url, dbUsername, password)) {

            int gradeID;

            String selectQuery = "SELECT gradeID from grades.GRADE WHERE grade = ?";

            try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
                statement.setFloat(1, grade);

                ResultSet resultSet = statement.executeQuery();

                resultSet.next();
                gradeID = resultSet.getInt(1);
            }

            String insertQuery = "INSERT INTO grades.SCHOOL_SUBJECT_GRADE (date, fk_grade, fk_subject) VALUES (?, ?, ?);";

            try (PreparedStatement statement1 = connection.prepareStatement(insertQuery)) {
                statement1.setString(1, dateString);
                statement1.setInt(2, gradeID);
                statement1.setInt(3, subjectID);

                ResultSet resultSet1 = statement1.executeQuery();
                System.out.println("The grade has been added!");
                System.out.println("");
            }
        }
    }

    private static void deleteGrade(String url, String dbUsername, String password) throws SQLException {
        System.out.println("");
        System.out.println("Which grade would you like to delete? (Enter the ID)");

        try (Connection connection = DriverManager.getConnection(url, dbUsername, password)) {

            String selectQuery = "SELECT date, school_subject_keysID, ss.subject as 'Subject', g.grade as 'Grade'\n" +
                    "From grades.SCHOOL_SUBJECT_GRADE ssg\n" +
                    "INNER JOIN grades.SCHOOL_SUBJECT ss\n" +
                    "on ssg.fk_subject = ss.subjectID\n" +
                    "INNER JOIN grades.GRADE g\n" +
                    "on ssg.fk_grade = g.gradeID;";

            try (Statement statement = connection.createStatement()) {
                List<Integer> allPrimaryKeys = new ArrayList<>();

                ResultSet resultSet = statement.executeQuery(selectQuery);

                int counter = 1;
                while (resultSet.next()) {
                    System.out.println(counter + ": Date: " + resultSet.getString(1) + " Subject: " + resultSet.getString(3) + " Grade: " + resultSet.getFloat(4));
                    allPrimaryKeys.add(resultSet.getInt(2));
                    counter++;
                }

                int whichGradeToDelete;
                do {
                    whichGradeToDelete = userInput.nextInt();
                } while (whichGradeToDelete < 1 || whichGradeToDelete > allPrimaryKeys.size());

                int primaryKeyOfGrade = allPrimaryKeys.get(whichGradeToDelete - 1);

                String deleteQuery = "DELETE FROM grades.SCHOOL_SUBJECT_GRADE WHERE school_subject_keysID = ?";

                try (PreparedStatement statement1 = connection.prepareStatement(deleteQuery)) {
                    statement1.setInt(1, primaryKeyOfGrade);

                    ResultSet resultSet1 = statement1.executeQuery();
                    System.out.println("The grade has been removed!");
                    System.out.println("");
                }
            }
        }
    }

    private static void updateGrade(String url, String dbUsername, String password) throws SQLException {
        System.out.println("");
        System.out.println("Which grade would you like to update? (Enter the ID)");

        try (Connection connection = DriverManager.getConnection(url, dbUsername, password)) {

            String selectQuery = "SELECT date, school_subject_keysID, ss.subject as 'Subject', g.grade as 'Grade'\n" +
                    "From grades.SCHOOL_SUBJECT_GRADE ssg\n" +
                    "INNER JOIN grades.SCHOOL_SUBJECT ss\n" +
                    "on ssg.fk_subject = ss.subjectID\n" +
                    "INNER JOIN grades.GRADE g\n" +
                    "on ssg.fk_grade = g.gradeID;";

            try (Statement statement = connection.createStatement()) {
                List<Integer> allPrimaryKeys = new ArrayList<>();

                ResultSet resultSet = statement.executeQuery(selectQuery);

                int counter = 1;
                while (resultSet.next()) {
                    System.out.println(counter + ": Date: " + resultSet.getString(1) + " Subject: " + resultSet.getString(3) + " Grade: " + resultSet.getFloat(4));
                    allPrimaryKeys.add(resultSet.getInt(2));
                    counter++;
                }

                int whichGradeToUpdate;
                do {
                    whichGradeToUpdate = userInput.nextInt();
                } while (whichGradeToUpdate < 1 || whichGradeToUpdate > allPrimaryKeys.size());

                int primaryKeyOfGrade = allPrimaryKeys.get(whichGradeToUpdate - 1);

                System.out.println("");
                System.out.println("What's your new grade?");
                System.out.println("Please note that only grades with 0.25 space are allowed.");
                System.out.println("For example: 4.5, 4.75, 5");
                float grade = userInput.nextFloat();
                System.out.println("");
                System.out.println("The date? (YYYY-MM-DD)");
                System.out.println("For example: \"2022-03-10\"");
                userInput.nextLine();
                String dateString = userInput.nextLine();
                System.out.println("");
                System.out.println("Now for which class did you achieve this grade?");
                System.out.println("1 - Math");
                System.out.println("2 - English");
                System.out.println("3 - PE");
                System.out.println("4 - Science");
                System.out.println("5 - History");
                int subjectID = userInput.nextInt();
                System.out.println("");

                int gradeID;

                String selectQuery1 = "SELECT gradeID from grades.GRADE WHERE grade = ?";

                try (PreparedStatement statement2 = connection.prepareStatement(selectQuery1)) {
                    statement2.setFloat(1, grade);

                    ResultSet resultSet1 = statement2.executeQuery();

                    resultSet1.next();
                    gradeID = resultSet1.getInt(1);
                }

                String updateQuery = "UPDATE grades.SCHOOL_SUBJECT_GRADE\n" +
                        "SET date = ?,\n" +
                        "SCHOOL_SUBJECT_GRADE.fk_grade = ?,\n" +
                        "SCHOOL_SUBJECT_GRADE.fk_subject = ?\n" +
                        "WHERE school_subject_keysID = ?;";

                try (PreparedStatement statement1 = connection.prepareStatement(updateQuery)) {
                    statement1.setString(1, dateString);
                    statement1.setInt(2, gradeID);
                    statement1.setInt(3, subjectID);
                    statement1.setInt(4, primaryKeyOfGrade);

                    ResultSet resultSet2 = statement1.executeQuery();
                    System.out.println("The grade has been updated!");
                    System.out.println("");
                }
            }
        }
    }

    public static void gradesOfOneClass(String url, String dbUsername, String password) throws SQLException {
        System.out.println("");
        System.out.println("Now for which class would you like to see your grades?");
        System.out.println("1 - Math");
        System.out.println("2 - English");
        System.out.println("3 - PE");
        System.out.println("4 - Science");
        System.out.println("5 - History");
        int subjectID = userInput.nextInt();
        System.out.println("");

        try (Connection connection = DriverManager.getConnection(url, dbUsername, password)) {

            String selectOneClassQuery = "SELECT grade\n" +
                    "from grades.GRADE g\n" +
                    "inner join grades.SCHOOL_SUBJECT_GRADE ssg\n" +
                    "on ssg.fk_grade = g.gradeID\n" +
                    "where fk_subject = ?;";

            try (PreparedStatement statement = connection.prepareStatement(selectOneClassQuery)) {
                statement.setInt(1, subjectID);

                ResultSet resultSet = statement.executeQuery();

                List<Float> allGradesFromSubject = new ArrayList<>();

                while (resultSet.next()) {
                    allGradesFromSubject.add(resultSet.getFloat(1));
                    System.out.println("Grades: " + resultSet.getFloat(1));
                }

                float allGradesTogether = 0;

                for (float grade : allGradesFromSubject) {
                    allGradesTogether += grade;
                }

                if (allGradesFromSubject.size() == 0) {
                    System.out.println("No grades available");
                } else {
                    System.out.println("Average grade: " + (allGradesTogether / allGradesFromSubject.size()));
                }
            }
        }
    }

    public static void reportCard(String url, String dbUsername, String password) throws SQLException {
        for (int i = 1; i < 6; i++) {
            try (Connection connection = DriverManager.getConnection(url, dbUsername, password)) {

                String selectOneClassQuery = "SELECT grade\n" +
                        "from grades.GRADE g\n" +
                        "inner join grades.SCHOOL_SUBJECT_GRADE ssg\n" +
                        "on ssg.fk_grade = g.gradeID\n" +
                        "where fk_subject = ?;";

                try (PreparedStatement statement = connection.prepareStatement(selectOneClassQuery)) {
                    statement.setInt(1, i);

                    ResultSet resultSet = statement.executeQuery();

                    List<Float> allGradesFromSubject = new ArrayList<>();

                    while (resultSet.next()) {
                        allGradesFromSubject.add(resultSet.getFloat(1));
                    }

                    float allGradesTogether = 0;

                    for (float grade : allGradesFromSubject) {
                        allGradesTogether += grade;
                    }

                    if (allGradesFromSubject.size() == 0) {
                        System.out.println("No grades available");
                    } else {
                        String whichSubject = "";
                        switch (i) {
                            case 1:
                                whichSubject = "Math: ";
                                break;
                            case 2:
                                whichSubject = "English: ";
                                break;
                            case 3:
                                whichSubject = "Pe: ";
                                break;
                            case 4:
                                whichSubject = "Science: ";
                                break;
                            case 5:
                                whichSubject = "History: ";
                        }

                        System.out.println("");
                        System.out.println("Average grade " + whichSubject + (allGradesTogether / allGradesFromSubject.size()));
                    }
                }
            }
        }
    }
}