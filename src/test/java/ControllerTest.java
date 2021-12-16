import controller.Controller;
import customexceptions.CustomExceptions;
import model.Course;
import model.Student;
import model.Teacher;
import repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private final List<Course> courses = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Teacher> teachers = new ArrayList<>();
    private CourseRepository courseRepository;
    private CourseRepository courseRepositorySpy;
    private StudentRepository studentRepository;
    private StudentRepository studentRepositorySpy;
    private TeacherRepository teacherRepository;
    private TeacherRepository teacherRepositorySpy;
    private Controller controller = null;

    @BeforeEach
    public void setup() throws SQLException {
        courseRepository = Mockito.mock(CourseRepository.class);
        studentRepository = Mockito.mock(StudentRepository.class);
        teacherRepository = Mockito.mock(TeacherRepository.class);

        controller = new Controller(courseRepository, studentRepository, teacherRepository);

        Course courseBazeDeDate = new Course(0, "Baze de date", 5, 6);
        Course courseStructuriDeDate = new Course(1, "Structuri de date si algoritmi", 2, 5);
        courses.add(courseBazeDeDate);
        courses.add(courseStructuriDeDate);
        Mockito.when(courseRepository.getAll()).thenReturn(courses);

        Student studentRazvan = new Student("Razvan", "Postescu", 103050, 0, List.of(0));
        Student studentMarius = new Student("Marius", "Pop", 103051, 0, List.of(1));
        Student studentAndrei = new Student("Andrei", "Marian", 103052, 0, List.of(0,1));
        students.add(studentRazvan);
        students.add(studentMarius);
        students.add(studentAndrei);
        Mockito.when(studentRepository.getAll()).thenReturn(students);

        Teacher teacherDorel = new Teacher("Dorel", "Bob", 1, List.of(0,1));
        teachers.add(teacherDorel);
        Mockito.when(teacherRepository.getAll()).thenReturn(teachers);


        Mockito.when(courseRepository.add(new Course(2, "Algebra", 7, 5))).thenReturn(new Course(2, "Algebra", 7, 5));
        Mockito.when(studentRepository.add(new Student("Codrut", "Irimie", 103053, 0, List.of(1,2)))).thenReturn(new Student("Codrut", "Irimie", 103053, 0, List.of(1,2)));
        Mockito.when(teacherRepository.add(new Teacher("Dor", "Dob", 2, List.of(2)))).thenReturn(new Teacher("Dor", "Dob", 2, List.of(2)));

        Mockito.when(courseRepository.find(0)).thenReturn(new Course(0, "Baze de date", 5, 6));
        Mockito.when(studentRepository.find(103050)).thenReturn(new Student("Razvan", "Postescu", 103050, 0, List.of(0)));
        Mockito.when(teacherRepository.find(1)).thenReturn(new Teacher("Dorel", "Bob", 1, List.of(0,1)));

        Mockito.when(courseRepository.update(new Course(0, "Baze de date", 5, 6),
                new Course(0, "Baze de date2", 15, 6)))
                        .thenReturn(new Course(0, "Baze de date2", 15, 6));

        Mockito.when(studentRepository.update(
                new Student("Razvan", "Postescu", 103050, 0, List.of(0)),
                new Student("Razvan", "Postescu", 103050, 0, List.of(0,1)))).
                thenReturn(new Student("Razvan", "Postescu", 103050, 0, List.of(0,1)));

        Mockito.when(teacherRepository.update(new Teacher("Dorel", "Bob", 1, List.of(0,1)),
                new Teacher("Dorel2", "Bob", 1, List.of(0,1)))).
                thenReturn(new Teacher("Dorel2", "Bob", 1, List.of(0,1)));

        courseRepositorySpy = Mockito.spy(courseRepository);
        Mockito.doNothing().when(courseRepositorySpy).delete(new Course(100, "Baze de date2", 15, 6));

        studentRepositorySpy = Mockito.spy(studentRepository);
        Mockito.doNothing().when(studentRepositorySpy).delete(new Student("Razvan", "Postescu", 103050999, 0, List.of(0)));

        teacherRepositorySpy = Mockito.spy(teacherRepository);
        Mockito.doNothing().when(teacherRepositorySpy).delete(new Teacher("Dorel", "Bob", 1999, List.of(0,1)));

        Mockito.when(studentRepository.find(103052)).thenReturn(new Student("Andrei", "Marian", 103052, 0, List.of(0,1)));

    }


    @Test
    void testGetCourses() throws SQLException {
        assertEquals(courses, controller.getCourses());
        Mockito.verify(courseRepository).getAll();
    }

    @Test
    void testGetStudents() throws SQLException {
        assertEquals(students, controller.getStudents());
        Mockito.verify(studentRepository).getAll();
    }

    @Test
    void testGetTeachers() throws SQLException {
        assertEquals(teachers, controller.getTeacher());
        Mockito.verify(teacherRepository).getAll();
    }

    @Test
    void testAddCourse() throws SQLException {
        assertEquals(new Course(2, "Algebra", 7, 5), controller.addCourse(2, "Algebra", 7, 5));
        Mockito.verify(courseRepository).add(new Course(2, "Algebra", 7, 5));

    }


    @Test
    void testAddStudent() throws SQLException {
        assertEquals(new Student("Codrut", "Irimie", 103053, 0, List.of(1,2)), controller.addStudent("Codrut", "Irimie", 103053, 0, List.of(1,2)));
        Mockito.verify(studentRepository).add(new Student("Codrut", "Irimie", 103053, 0, List.of(1,2)));
        
    }

    @Test
    void testAddTeacher() throws SQLException {
        assertEquals(new Teacher("Dor", "Dob", 2, List.of(2)), controller.addTeacher("Dor", "Dob", 2, List.of(2)));
        Mockito.verify(teacherRepository).add(new Teacher("Dor", "Dob", 2, List.of(2)));
        
    }

    @Test
    void testUpdateCourse() throws CustomExceptions, SQLException {
        assertEquals(new Course(0, "Baze de date2", 15, 6), controller.updateCourse(0, 0, "Baze de date2", 15, 6));
        Mockito.verify(courseRepository).update(new Course(0, "Baze de date", 5, 6), new Course(0, "Baze de date2", 15, 6));

    }

    @Test
    void testUpdateStudent() throws CustomExceptions, SQLException {
        assertEquals(new Student("Razvan", "Postescu", 103050, 0, List.of(0,1)), controller.updateStudent(103050, "Razvan", "Postescu", 103050, 0, List.of(0,1)));
        Mockito.verify(studentRepository).update(new Student("Razvan", "Postescu", 103050, 0, List.of(0)), new Student("Razvan", "Postescu", 103050, 0, List.of(0,1)));

    }

    @Test
    void testUpdateTeacher() throws CustomExceptions, SQLException {
        assertEquals(new Teacher("Dorel2", "Bob", 1, List.of(0,1)), controller.updateTeacher(1, "Dorel2", "Bob", 1, List.of(0,1)));
        Mockito.verify(teacherRepository).update(new Teacher("Dorel", "Bob", 1, List.of(0,1)), new Teacher("Dorel2", "Bob", 1, List.of(0,1)));
        
    }

    @Test
    void testDeleteCourse() throws SQLException {
        Controller controller1 = new Controller(courseRepositorySpy, studentRepository, teacherRepository);
        controller1.deleteCourse(new Course(100, "Baze de date2", 15, 6));
        Mockito.verify(courseRepositorySpy).delete(new Course(100, "Baze de date2", 15, 6));

    }

    @Test
    void testDeleteStudent() throws SQLException {
        Controller controller1 = new Controller(courseRepository, studentRepositorySpy, teacherRepository);
        controller1.deleteStudent(new Student("Razvan", "Postescu", 103050999, 0, List.of(0)));
        Mockito.verify(studentRepositorySpy).delete(new Student("Razvan", "Postescu", 103050999, 0, List.of(0)));

    }

    @Test
    void testDeleteTeacher() throws SQLException {
        Controller controller1 = new Controller(courseRepository, studentRepository, teacherRepositorySpy);
        controller1.deleteTeacher(new Teacher("Dorel", "Bob", 1999, List.of(0,1)));
        Mockito.verify(teacherRepositorySpy).delete(new Teacher("Dorel", "Bob", 1999, List.of(0,1)));

    }

    @Test
    void testSortStudentsByEnrolledCourses() throws CustomExceptions, SQLException {
        assertNotEquals(controller.sortStudentsByEnrolledCourses(), controller.getStudents());
        assertEquals(controller.findStudentById(103052), controller.sortStudentsByEnrolledCourses().get(0));
        
    }

    @Test
    void testSortCourseByCredits() throws SQLException {
        assertEquals(controller.sortCourseByCredits(), controller.getCourses());
        
    }

    @Test
    void testFilterStudentsByLessThenXCourses() throws SQLException {
        assertNotEquals(controller.filterStudentsByLessThenXCourses(1), controller.getStudents());
        for (Student s:
                controller.filterStudentsByLessThenXCourses(1)) {
            assertTrue(s.getEnrolledCourses().size() <= 1);
        }
        
    }

    @Test
    void testFilterCourseByMaxEnrollment() throws SQLException {
        assertNotEquals(controller.filterCourseByMaxEnrollment(2), controller.getCourses());
        for (Course c:
             controller.filterCourseByMaxEnrollment(2)) {
            assertTrue(c.getMaxEnrollment() <= 2);
        }

        
    }





}

