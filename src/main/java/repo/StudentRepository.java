package repo;

import model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Repository for Student
 */
public class StudentRepository extends SQLRepository<Student> {

    /**
     * Default constructor for StudentRepository
     * @throws SQLException when connection to DB was unsuccessful
     */
    public StudentRepository() throws SQLException {
        super();
    }

    /**
     * Add method for storing Student in DB
     * @param obj Student Object given as parameter
     * @return obj
     * @throws SQLException in case of invalid input
     */
    @Override
    public Student add(Student obj) throws SQLException {
        String sqlQuery = "insert into student values (" + obj.getStudentId() +
                ", '" + obj.getFirstName() + "', '" + obj.getLastName() + "', " + obj.getTotalCredits() + ")";
        String sqlQuery2;
        statement.execute(sqlQuery);
        for (Integer id :
                obj.getEnrolledCourses()) {
            sqlQuery2 = "insert into students_courses values (" + obj.getStudentId() + ", " + id + ")";
            statement.execute(sqlQuery2);
        }
        return obj;
    }

    /**
     * GetAll method for retrieving all Students from DB
     * @return List<Student>
     * @throws SQLException
     */
    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sqlQuery = "select * from student";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        String sqlQuery2;
        ResultSet resultSet2;
        while (resultSet.next()) {
            Student tempStudent = new Student();
            tempStudent.setStudentId(resultSet.getInt("studentId"));
            tempStudent.setFirstName(resultSet.getString("firstName"));
            tempStudent.setLastName(resultSet.getString("lastName"));
            tempStudent.setTotalCredits(resultSet.getInt("totalCredits"));
            sqlQuery2 = "select courseId from students_courses where studentId = " + tempStudent.getStudentId();
            resultSet2 = secondStatement.executeQuery(sqlQuery2);
            List<Integer> courses = new ArrayList<>();
            while (resultSet2.next()) {
                courses.add(resultSet2.getInt("courseId"));
            }
            tempStudent.setEnrolledCourses(courses);
            students.add(tempStudent);
        }
        return students;
    }

    /**
     * Retrieve Method from Student by id
     * @param id int
     * @return Student
     * @throws SQLException
     */
    @Override
    public Student find(Integer id) throws SQLException {
        Student tempStudent = new Student();
        String sqlQuery = "select * from student where studentId=" + id;
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        resultSet.next();
        tempStudent.setStudentId(resultSet.getInt("studentID"));
        tempStudent.setFirstName(resultSet.getString("firstName"));
        tempStudent.setLastName(resultSet.getString("lastName"));
        tempStudent.setTotalCredits(resultSet.getInt("totalCredits"));
        String sqlQuery2 = "select sum(credits) as sumCredits from course \n" +
                "inner join students_courses on course.courseId = students_courses.courseId\n" +
                "inner join student on student.studentId = students_courses.studentId\n" +
                "where student.studentId = " + tempStudent.getStudentId();
        resultSet = statement.executeQuery(sqlQuery2);
        resultSet.next();
        tempStudent.setTotalCredits(resultSet.getInt("sumCredits"));

        sqlQuery2 = "select courseId from students_courses where studentId = " + tempStudent.getStudentId();
        ResultSet resultSet2 = secondStatement.executeQuery(sqlQuery2);
        List<Integer> courses = new ArrayList<>();
        int credits = 0;
        while (resultSet2.next()) {
            courses.add(resultSet2.getInt("courseId"));
        }
        tempStudent.setEnrolledCourses(courses);
        return tempStudent;
    }

    /**
     * Update Method for Student in DB
     * @param oldObject Old Student
     * @param newObject New Student
     * @return New Student
     * @throws SQLException in case of invalid input
     */
    @Override
    public Student update(Student oldObject, Student newObject) throws SQLException {
        if (!oldObject.getEnrolledCourses().isEmpty()) {
            String sqlQuery2 = "delete from students_courses where studentId=" + oldObject.getStudentId();
            statement.execute(sqlQuery2);
        }

        String sqlQuery = "select * from student where studentId=" + oldObject.getStudentId();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        resultSet.next();

        if (!Objects.equals(oldObject.getStudentId(), newObject.getStudentId())) {
            String sqlQuery2 = "update student set studentId=" + newObject.getStudentId() + " where studentId=" + oldObject.getStudentId();
            statement.execute(sqlQuery2);
        }

        if (!Objects.equals(oldObject.getFirstName(), newObject.getFirstName())) {
            String sqlQuery2 = "update student set firstName='" + newObject.getFirstName() + "' where studentId=" + newObject.getStudentId();
            statement.execute(sqlQuery2);
        }

        if (!Objects.equals(oldObject.getLastName(), newObject.getLastName())) {
            String sqlQuery2 = "update student set lastName='" + newObject.getLastName() + "' where studentId=" + newObject.getStudentId();
            statement.execute(sqlQuery2);
        }

        if (!Objects.equals(oldObject.getTotalCredits(), newObject.getTotalCredits())) {
            String sqlQuery2 = "update student set totalCredits=" + newObject.getTotalCredits() + " where studentId=" + newObject.getStudentId();
            statement.execute(sqlQuery2);
        }

        if (!newObject.getEnrolledCourses().isEmpty()) {
            for (Integer id :
                    newObject.getEnrolledCourses()) {
                String sqlQuery2 = "insert into students_courses values (" + newObject.getStudentId() + ", " + id + ")";
                statement.execute(sqlQuery2);
            }
        }

        return newObject;
    }

    /**
     * Delete Method for Student in DB
     * @param obj Deleted Student
     * @throws SQLException in case of invalid input
     */
    @Override
    public void delete(Student obj) throws SQLException {
        String sqlQuery = "delete from student where studentId=" + obj.getStudentId();
        statement.execute(sqlQuery);
        String sqlQuery2 = "delete from students_courses where studentId=" + obj.getStudentId();
        statement.execute(sqlQuery2);
    }

}

