
package repo;

import model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository for Course
 */
public class CourseRepository extends SQLRepository<Course> {

    /**
     * Default constructor for CourseRepository
     * @throws SQLException when connection to DB was unsuccessful
     */
    public CourseRepository() throws SQLException {
        super();
    }

    /**
     * Add method for storing Course in DB
     * @param obj Course Object given as parameter
     * @return obj
     * @throws SQLException in case of invalid input
     */
    @Override
    public Course add(Course obj) throws SQLException {
        String sqlQuery = "insert into course values(" + obj.getId() + ", '" + obj.getName() +
                "', " + obj.getTeacher() + ", " + obj.getMaxEnrollment() + ", " + obj.getCredits() + ") ";
        statement.execute(sqlQuery);
        return obj;
    }

    /**
     * GetAll method for retrieving all Courses from DB
     * @return List<Course>
     * @throws SQLException
     */
    @Override
    public List<Course> getAll() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sqlQuery = "select * from course";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        String sqlQuery2;
        ResultSet resultSet2;
        while (resultSet.next()) {
            Course tempCourse = new Course();
            tempCourse.setId(resultSet.getInt("courseId"));
            tempCourse.setName(resultSet.getString("name"));
            tempCourse.setTeacher(resultSet.getInt("teacherId"));
            tempCourse.setMaxEnrollment(resultSet.getInt("maxEnrollment"));
            tempCourse.setCredits(resultSet.getInt("credits"));
            sqlQuery2 = "select * from students_courses where courseId = " + tempCourse.getId();
            resultSet2 = secondStatement.executeQuery(sqlQuery2);
            List<Integer> students = new ArrayList<>();
            while (resultSet2.next()) {
                students.add(resultSet2.getInt("studentId"));
            }
            tempCourse.setStudentsEnrolled(students);
            courses.add(tempCourse);
        }
        return courses;
    }

    /**
     * Retrieve Method from Course by id
     * @param id int
     * @return Course
     * @throws SQLException
     */
    @Override
    public Course find(Integer id) throws SQLException {
        Course tempCourse = new Course();
        String sqlQuery = "select * from course where courseId = " + id;
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        resultSet.next();
        tempCourse.setId(resultSet.getInt("courseId"));
        tempCourse.setName(resultSet.getString("name"));
        tempCourse.setTeacher(resultSet.getInt("teacherId"));
        tempCourse.setMaxEnrollment(resultSet.getInt("maxEnrollment"));
        tempCourse.setCredits(resultSet.getInt("credits"));
        String sqlQuery2 = "select * from students_courses where courseId = " + tempCourse.getId();
        ResultSet resultSet2 = secondStatement.executeQuery(sqlQuery2);
        List<Integer> students = new ArrayList<>();
        while (resultSet2.next()) {
            students.add(resultSet2.getInt("studentId"));
        }
        tempCourse.setStudentsEnrolled(students);
        return tempCourse;
    }

    /**
     * Update Method for Course in DB
     * @param oldObject Old Course
     * @param newObject New Course
     * @return New Course
     * @throws SQLException in case of invalid input
     */
    @Override
    public Course update(Course oldObject, Course newObject) throws SQLException {
        if (!oldObject.getStudentsEnrolled().isEmpty()) {
            String sqlQuery2 = "delete from students_courses where courseId = " + oldObject.getId();
            statement.execute(sqlQuery2);
        }

        String sqlQuery = "select * from course where courseId = " + oldObject.getId();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        resultSet.next();

        if (!Objects.equals(oldObject.getId(), newObject.getId())) {
            String sqlQuery2 = "update course set courseId = " + newObject.getId() + " where courseId = " + oldObject.getId();
            statement.execute(sqlQuery2);
        }

        if (!Objects.equals(oldObject.getName(), newObject.getName())) {
            String sqlQuery2 = "update course set name = '" + newObject.getName() + "' where courseId = " + newObject.getId();
            statement.execute(sqlQuery2);
        }

        if (!Objects.equals(oldObject.getMaxEnrollment(), newObject.getMaxEnrollment())) {
            String sqlQuery2 = "update course set maxEnrollment = " + newObject.getMaxEnrollment() + " where courseId = " + newObject.getId();
            statement.execute(sqlQuery2);
        }

        if (!Objects.equals(oldObject.getCredits(), newObject.getCredits())) {
            String sqlQuery2 = "update course set credits = " + newObject.getCredits() + " where courseId = " + newObject.getId();
            statement.execute(sqlQuery2);
        }

        if (newObject.getStudentsEnrolled() != null) {
            for (Integer id :
                    newObject.getStudentsEnrolled()) {
                String sqlQuery2 = "insert into students_courses values (" + id + ", " + newObject.getId() + ")";
                statement.execute(sqlQuery2);
            }
        }

        return newObject;
    }

    /**
     * Delete Method for Course in DB
     * @param obj Course Teacher
     * @throws SQLException in case of invalid input
     */
    @Override
    public void delete(Course obj) throws SQLException {
        String sqlQuery = "delete from students_courses where courseId = " + obj.getId();
        statement.execute(sqlQuery);
        String sqlQuery2 = "delete from course where courseId = " + obj.getId();
        statement.execute(sqlQuery2);
    }
}

