
package view;

import controller.Controller;
import customexceptions.CustomExceptions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * UI
 * Main method runs the user interface
 */
public class Console {
    private final Controller controller;
    private static final String INVALID_INPUT_EXCEPTION = "Invalid input!";
    private static final String SELECT_COURSE_BY_ID = "Select the Course(s) by entering Course id:";

    public Console(Controller controller) {
        this.controller = controller;
    }


    /**
     * Print all Courses Method
     */
    public void showCourses() throws SQLException {
        controller.getCourses().forEach(System.out::println);
    }


    /**
     * Print all Students Method
     */
    public void showStudents() throws SQLException {
        controller.getStudents().forEach(System.out::println);
    }


    /**
     * Print all Teachers Method
     */
    public void showTeachers() throws SQLException {
        controller.getTeacher().forEach(System.out::println);
    }


    /**
     * Print Students sorted by count(enrolledCourses) in descending order
     */
    public void showSortStudentsByEnrolledCourses() throws SQLException {
        controller.sortStudentsByEnrolledCourses().forEach(System.out::println);
    }


    /**
     * Print Courses sorted by credits in descending order
     */
    public void showSortCourseByCredits() throws SQLException {
        controller.sortCourseByCredits().forEach(System.out::println);
    }


    /**
     * Update Course Method
     *
     * @throws CustomExceptions in case of Course not found or invalid input
     */
    public void updateCourse() throws CustomExceptions, SQLException {
        Scanner scanner = new Scanner(System.in);
        showCourses();
        System.out.println("Enter id of Course you want to update:");
        try {
            int oldId = scanner.nextInt();
            System.out.println("Now you will need to write a few things that represent Course attributes.");
            System.out.println("Enter Course id:");
            int id = scanner.nextInt();
            System.out.println("Enter Course name:");
            scanner.nextLine(); //throw away the \n not consumed by nextInt()
            String name = scanner.nextLine();
            System.out.println("Enter maximum number of students that can be enrolled:");
            int maxEnrollment = scanner.nextInt();
            System.out.println("Enter Course credits:");
            int credits = scanner.nextInt();
            controller.updateCourse(oldId, id, name, maxEnrollment, credits);

        } catch (Exception e) {
            throw (new CustomExceptions(INVALID_INPUT_EXCEPTION));
        }
    }


    /**
     * Update Student Method
     *
     * @throws CustomExceptions in case of Student not found or invalid input
     */
    public void updateStudent() throws CustomExceptions, SQLException {
        Scanner scanner = new Scanner(System.in);
        showStudents();
        System.out.println("Enter id of Student you want to update:");

        try {
            int oldId = scanner.nextInt();
            System.out.println("You will need to write a few things that represent Student attributes.");
            System.out.println("Enter Student id:");
            int id = scanner.nextInt();
            System.out.println("Enter Student first name:");
            scanner.nextLine(); //throw away the \n not consumed by nextInt()
            String firstName = scanner.nextLine();
            System.out.println("Enter Student last name:");
            String lastName = scanner.nextLine();
            System.out.println("Please input how many courses do you wish the Student to be enrolled: ");
            int coursesCount = scanner.nextInt();
            List<Integer> coursesIds = new ArrayList<>();
            showCourses();
            for (int i = 0; i < coursesCount; i++) {
                System.out.println(SELECT_COURSE_BY_ID);
                Integer courseId = scanner.nextInt();
                coursesIds.add(courseId);
            }
            controller.updateStudent(oldId, firstName, lastName, id, 0, coursesIds);

        } catch (Exception e) {
            throw (new CustomExceptions(INVALID_INPUT_EXCEPTION));
        }

    }


    /**
     * Update Teacher Method
     *
     * @throws CustomExceptions in case of Teacher not found or invalid input
     */
    public void updateTeacher() throws CustomExceptions, SQLException {
        Scanner scanner = new Scanner(System.in);
        showTeachers();
        System.out.println("Enter id of Teacher you want to update:");
        try {
            int oldId = scanner.nextInt();
            System.out.println("You will need to write a few things that represent Teacher attributes.");
            System.out.println("Enter Teacher id:");
            int id = scanner.nextInt();
            System.out.println("Enter Teacher first name:");
            scanner.nextLine(); //throw away the \n not consumed by nextInt()
            String firstName = scanner.nextLine();
            System.out.println("Enter Teacher last name:");
            String lastName = scanner.nextLine();
            System.out.println("Please input how many courses do you wish the Teacher to teach: ");
            int coursesCount = scanner.nextInt();
            List<Integer> coursesIds = new ArrayList<>();
            showCourses();
            for (int i = 0; i < coursesCount; i++) {
                System.out.println(SELECT_COURSE_BY_ID);
                Integer courseId = scanner.nextInt();
                coursesIds.add(courseId);
            }
            controller.updateTeacher(oldId, firstName, lastName, id, coursesIds);
        } catch (Exception e) {
            throw (new CustomExceptions(INVALID_INPUT_EXCEPTION));
        }

    }


    /**
     * Add Course Method
     */
    public void addCourse() throws CustomExceptions {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You will need to write a few things that represent Course attributes.");
        System.out.println("Enter Course id:");
        try {
            int id = scanner.nextInt();
            System.out.println("Enter Course name:");
            scanner.nextLine(); //throw away the \n not consumed by nextInt()
            String name = scanner.nextLine();
            System.out.println("Enter maximum number of students that can be enrolled:");
            int maxEnrollment = scanner.nextInt();
            System.out.println("Enter Course credits:");
            int credits = scanner.nextInt();
            controller.addCourse(id, name, maxEnrollment, credits);
        } catch (Exception e) {
            throw (new CustomExceptions(INVALID_INPUT_EXCEPTION));
        }

    }


    /**
     * Add Student Method
     */
    public void addStudent() throws CustomExceptions {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You will need to write a few things that represent Student attributes.");

        try {
            System.out.println("Enter Student id:");
            int id = scanner.nextInt();
            System.out.println("Enter Student first name:");
            scanner.nextLine(); //throw away the \n not consumed by nextInt()
            String firstName = scanner.nextLine();
            System.out.println("Enter Student last name:");
            String lastName = scanner.nextLine();
            System.out.println("Please input how many courses do you wish the Student to be enrolled: ");
            int coursesCount = scanner.nextInt();
            List<Integer> coursesIds = new ArrayList<>();
            showCourses();
            for (int i = 0; i < coursesCount; i++) {
                System.out.println(SELECT_COURSE_BY_ID);
                Integer courseId = scanner.nextInt();
                coursesIds.add(courseId);
            }
            controller.addStudent(firstName, lastName, id, 0, coursesIds);

        } catch (Exception e) {
            throw (new CustomExceptions(INVALID_INPUT_EXCEPTION));
        }
    }


    /**
     * Teacher Add Method
     *
     * @throws CustomExceptions in case of invalid input
     */
    public void addTeacher() throws CustomExceptions {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You will need to write a few things that represent Teacher attributes.");

        try {
            System.out.println("Enter Teacher id:");
            int id = scanner.nextInt();
            System.out.println("Enter Teacher first name:");
            scanner.nextLine(); //throw away the \n not consumed by nextInt()
            String firstName = scanner.nextLine();
            System.out.println("Enter Teacher last name:");
            String lastName = scanner.nextLine();
            System.out.println("Please input how many courses do you wish the Teacher to teach: ");
            int coursesCount = scanner.nextInt();
            List<Integer> coursesIds = new ArrayList<>();
            showCourses();
            for (int i = 0; i < coursesCount; i++) {
                System.out.println(SELECT_COURSE_BY_ID);
                Integer courseId = scanner.nextInt();
                coursesIds.add(courseId);
            }
            controller.addTeacher(firstName, lastName, id, coursesIds);
        } catch (Exception e) {
            throw (new CustomExceptions(INVALID_INPUT_EXCEPTION));
        }
    }


    /**
     * Course delete Method
     *
     * @throws CustomExceptions in case of invalid input
     */
    public void deleteCourse() throws CustomExceptions, SQLException {
        Scanner scanner = new Scanner(System.in);
        showCourses();
        System.out.println("ID of Course you want to delete:");
        try {
            int id = scanner.nextInt();
            controller.deleteCourse(controller.findCourseById(id));
        } catch (Exception e) {
            throw (new CustomExceptions(INVALID_INPUT_EXCEPTION));
        }

    }


    /**
     * Student delete Method
     *
     * @throws CustomExceptions in case of invalid input
     */
    public void deleteStudent() throws CustomExceptions, SQLException {
        Scanner scanner = new Scanner(System.in);
        showStudents();
        System.out.println("ID of Student you want to delete:");
        try {
            int id = scanner.nextInt();
            controller.deleteStudent(controller.findStudentById(id));
        } catch (Exception e) {
            throw (new CustomExceptions(INVALID_INPUT_EXCEPTION));
        }
    }


    /**
     * Teacher delete Method
     *
     * @throws CustomExceptions in case of invalid input
     */
    public void deleteTeacher() throws CustomExceptions, SQLException {
        Scanner scanner = new Scanner(System.in);
        showTeachers();
        System.out.println("ID of Teacher you want to delete:");
        try {
            int id = scanner.nextInt();
            controller.deleteTeacher(controller.findTeacherById(id));
        } catch (Exception e) {
            throw (new CustomExceptions(INVALID_INPUT_EXCEPTION));
        }
    }


    /**
     * filterStudentsByLessThenXCourses print Method
     */
    public void showFilterStudentsByLessThenXCourses() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Students with less or equal then X enrolled courses will be show.");
        System.out.println("Insert X value: ");
        int temp = scanner.nextInt();
        controller.filterStudentsByLessThenXCourses(temp).forEach(System.out::println);
    }


    /**
     * filterCourseByMaxEnrollment print Method
     */
    public void showFilterCourseByMaxEnrollment() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Courses with less or equal then X maximum enrollment will be show.");
        System.out.println("Insert X value: ");
        int temp = scanner.nextInt();
        controller.filterCourseByMaxEnrollment(temp).forEach(System.out::println);
    }


    /**
     * Main UI run loop
     *
     * @throws CustomExceptions in case of invalid input
     */
    public void run() throws CustomExceptions, SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                     
                     0. Exit Program \r
                     1. Show all Courses \r
                     2. Show all Students \r
                     3. Show all Teachers \r
                     4. Add a Course \r
                     5. Add a Student \r
                     6. Add a Teacher \r
                     7. Delete a Course \r
                     8. Delete a Student \r
                     9. Delete a Teacher \r
                     10. Update a Course \r
                     11. Update a Student \r
                     12. Update a Teacher \r
                     13. Sort students by most enrolled Student \r
                     14. Sort courses by credits \r
                     15. Filter Students by less or equal then a given number of enrollments\s
                     16. Filter Courses by less or equal then a given number of enrollments\s
                    """);
            System.out.println("Enter input: ");
            int variant = scanner.nextInt();
            System.out.println("You've entered: " + variant);
            if (variant == 0)
                break;
            else if (variant == 1)
                showCourses();
            else if (variant == 2)
                showStudents();
            else if (variant == 3)
                showTeachers();
            else if (variant == 4)
                addCourse();
            else if (variant == 5)
                addStudent();
            else if (variant == 6)
                addTeacher();
            else if (variant == 7)
                deleteCourse();
            else if (variant == 8)
                deleteStudent();
            else if (variant == 9)
                deleteTeacher();
            else if (variant == 10)
                updateCourse();
            else if (variant == 11)
                updateStudent();
            else if (variant == 12)
                updateTeacher();
            else if (variant == 13)
                showSortStudentsByEnrolledCourses();
            else if (variant == 14)
                showSortCourseByCredits();
            else if (variant == 15) {
                showFilterStudentsByLessThenXCourses();
            } else if (variant == 16) {
                showFilterCourseByMaxEnrollment();
            } else
                throw (new CustomExceptions(INVALID_INPUT_EXCEPTION));
        }
    }
}

