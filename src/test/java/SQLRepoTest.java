/* Not needed I guess?
import CustomExceptions.CustomExceptions;
import Model.Student;
import Repo.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLRepoTest {
    private StudentRepository studentRepository;

    @BeforeEach
    void setup() throws SQLException {
        studentRepository = new StudentRepository();
    }

    @Test
    void testGetAll() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        studentList = studentRepository.getAll();
        for (Student s:
             studentList) {
            System.out.println(s);
        }
    }

    @Test
    void testFindById() throws SQLException, CustomExceptions {
        studentRepository.find(0);
        studentRepository.find(1);
        studentRepository.find(2);
        studentRepository.find(3);
    }

    @Test
    void testDelete() throws SQLException {
        studentRepository.delete(new Student("whatever", "whatever", 0, 0, new ArrayList<>()));
    }

    @Test
    void testUpdate() throws SQLException {
        List<Integer> courseList = new ArrayList<>();
        List<Integer> courseList2 = new ArrayList<>();
        courseList.add(1);
        courseList.add(0);
        courseList2.add(0);
        studentRepository.update(new Student("marccel", "del", 1, 9, courseList),
                new Student("marcel", "del", 1, 19, courseList2));
    }



}
*/
