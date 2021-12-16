package controller;

import customexceptions.CustomExceptions;
import model.Course;
import model.Student;
import model.Teacher;
import repo.CourseRepository;
import repo.StudentRepository;
import repo.TeacherRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


/**
 * Controller Class
 */
public class Controller {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;


    /**
     * Controller makes use of 3 FileRepositories
     *
     * @param courseFileRepository: Repository for Course
     * @param studentFileRepository: Repository for Student
     * @param teacherFileRepository: Repository for Teacher
     *
     */
    public Controller(CourseRepository courseFileRepository, StudentRepository studentFileRepository, TeacherRepository teacherFileRepository) {
        this.courseRepository = courseFileRepository;
        this.teacherRepository = teacherFileRepository;
        this.studentRepository = studentFileRepository;

    }


    /**
     * Getter for all courses
     *
     * @return List<Course>
     */
    public List<Course> getCourses() throws SQLException {
        return courseRepository.getAll();
    }


    /**
     * Getter for all students
     *
     * @return List<Student>
     */
    public List<Student> getStudents() throws SQLException {
        return studentRepository.getAll();
    }


    /**
     * Getter for all teachers
     *
     * @return List<Teacher>
     */
    public List<Teacher> getTeacher() throws SQLException {
        return teacherRepository.getAll();
    }

    public Course addCourse(int id, String name, int maxEnrollment, int credits) throws SQLException {
        return courseRepository.add(new Course(id, name, maxEnrollment, credits));
    }


    /**
     * Student add Method
     *
     * @param firstName:     String
     * @param lastName:      String
     * @param studentId:    Int
     * @param totalCredits: Int
     * @param coursesId:     List<Integer>
     * @return Student
     */
    public Student addStudent(String firstName, String lastName, int studentId, int totalCredits, List<Integer> coursesId) throws SQLException {
        return studentRepository.add(new Student(firstName, lastName, studentId, totalCredits, coursesId));
    }


    /**
     * Teacher add Method
     *
     * @param firstName:  String
     * @param lastName:   String
     * @param teacherId: Int
     * @param courses:   List<Integer>
     * @return Teacher
     */
    public Teacher addTeacher(String firstName, String lastName, int teacherId, List<Integer> courses) throws SQLException {
        return teacherRepository.add(new Teacher(firstName, lastName, teacherId, courses));
    }


    /**
     * Find by id Method for Course
     *
     * @param id: Int
     * @return Course
     */
    public Course findCourseById(int id) throws SQLException {
        return courseRepository.find(id);
    }


    /**
     * Find Student by Id
     *
     * @param id: Int
     * @return Student
     */
    public Student findStudentById(int id) throws SQLException {
        return studentRepository.find(id);
    }


    /**
     * Find Teacher by Id
     *
     * @param id: Int
     * @return Teacher
     */
    public Teacher findTeacherById(int id) throws SQLException {
        return teacherRepository.find(id);
    }


    /**
     * Delete Course Method
     *
     * @param course: Course
     */
    public void deleteCourse(Course course) throws SQLException {
        courseRepository.delete(course);
    }


    /**
     * Delete Student Method
     *
     * @param student: Student
     */
    public void deleteStudent(Student student) throws SQLException {
        studentRepository.delete(student);
    }


    /**
     * Delete Teacher Method
     *
     * @param teacher: Teacher
     */
    public void deleteTeacher(Teacher teacher) throws SQLException {
        teacherRepository.delete(teacher);
    }


    /**
     * Update Course Method
     *
     * @param oldCourseId:   Id of the Course that needs to be updated
     * @param id:            New Id
     * @param name:          String
     * @param maxEnrollment: Int
     * @param credits:       Int
     * @throws CustomExceptions in case of Course not found
     */
    public Course updateCourse(int oldCourseId, int id, String name, int maxEnrollment, int credits) throws CustomExceptions, SQLException {
        Course tempCourse = findCourseById(oldCourseId);
        if (Objects.equals(name, ""))
            name = tempCourse.getName();

        return courseRepository.update(tempCourse, new Course(id, name, maxEnrollment, credits));
    }


    /**
     * Update Student Method
     *
     * @param oldStudentId:  Id of Student that needs to be updated
     * @param firstName:     String
     * @param lastName:      String
     * @param studentId:    Int
     * @param totalCredits: Int
     * @param coursesId:     List<Integer>
     * @throws CustomExceptions in case of Student not found
     */
    public Student updateStudent(long oldStudentId, String firstName, String lastName, int studentId, int totalCredits, List<Integer> coursesId) throws CustomExceptions, SQLException {
        Student tempStudent = findStudentById((int) oldStudentId);
        if (Objects.equals(firstName, ""))
            firstName = tempStudent.getFirstName();
        if (Objects.equals(lastName, ""))
            lastName = tempStudent.getLastName();

        return studentRepository.update(tempStudent, new Student(firstName, lastName, studentId, totalCredits, coursesId));
    }


    /**
     * Update Teacher Method
     *
     * @param oldId:      Id of Teacher that needs to be updated
     * @param firstName:  String
     * @param lastName:   String
     * @param teacherId: Int
     * @param courses:   List<Integer>
     * @throws CustomExceptions in case of Teacher not found
     */
    public Teacher updateTeacher(int oldId, String firstName, String lastName, int teacherId, List<Integer> courses) throws CustomExceptions, SQLException {
        Teacher tempTeacher = findTeacherById(oldId);
        if (Objects.equals(firstName, ""))
            firstName = tempTeacher.getFirstName();
        if (Objects.equals(lastName, ""))
            lastName = tempTeacher.getLastName();

        return teacherRepository.update(tempTeacher, new Teacher(firstName, lastName, teacherId, courses));


    }


    /**
     * Sort students descending by
     * ammount of enrolled courses
     *
     * @return List<Student>
     */
    public List<Student> sortStudentsByEnrolledCourses() throws SQLException {
        return studentRepository.getAll().stream().sorted((x, y) -> (y.getEnrolledCourses().size() - x.getEnrolledCourses().size())).toList();
    }


    /**
     * Sort courses descending by
     * amount of credits
     *
     * @return List<Course>
     */
    public List<Course> sortCourseByCredits() throws SQLException {
        return courseRepository.getAll().stream().sorted((x, y) -> (y.getCredits() - x.getCredits())).toList();
    }


    /**
     * Filter students by
     * max amount of enrolled Courses
     *
     * @param coursesCount: user given integer for filter
     * @return List<Student>
     */
    public List<Student> filterStudentsByLessThenXCourses(Integer coursesCount) throws SQLException {
        return studentRepository.getAll().stream().filter(x -> x.getEnrolledCourses().size() <= coursesCount).toList();
    }


    /**
     * Filter courses by
     * max amount of enrollment
     *
     * @param enrollmentCount: user given integer for filter
     * @return List<Course>
     */
    public List<Course> filterCourseByMaxEnrollment(Integer enrollmentCount) throws SQLException {
        return courseRepository.getAll().stream().filter(x -> x.getMaxEnrollment() <= enrollmentCount).toList();
    }

}

