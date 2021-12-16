package repo;

import model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository for Teacher
 */
public class TeacherRepository extends SQLRepository<Teacher> {

    /**
     * Default constructor for TeacherRepository
     * @throws SQLException when connection to DB was unsuccessful
     */
    public TeacherRepository() throws SQLException {
        super();
    }

    /**
     * Add method for storing Teacher in DB
     * @param obj Teacher Object given as parameter
     * @return obj
     * @throws SQLException in case of invalid input
     */
    @Override
    public Teacher add(Teacher obj) throws SQLException {
        String sqlQuery = "insert into teacher values(" + obj.getTeacherId() +
                ", '" + obj.getFirstName() + "', '" + obj.getLastName() + "')";
        statement.execute(sqlQuery);
        String sqlQuery2;
        if (!obj.getCourses().isEmpty()) {
            for (Integer id :
                    obj.getCourses()) {
                sqlQuery2 = "update course set teacherId = " + obj.getTeacherId() + " where courseId = " + id;
                statement.execute(sqlQuery2);
            }
        }
        return obj;
    }

    /**
     * GetAll method for retrieving all Teachers from DB
     * @return List<Teacher>
     * @throws SQLException
     */
    @Override
    public List<Teacher> getAll() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String sqlQuery = "select * from teacher";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        String sqlQuery2;
        ResultSet resultSet2;
        while (resultSet.next()) {
            Teacher tempTeacher = new Teacher();
            tempTeacher.setTeacherId(resultSet.getInt("teacherId"));
            tempTeacher.setFirstName(resultSet.getString("firstName"));
            tempTeacher.setLastName(resultSet.getString("lastName"));
            sqlQuery2 = "select courseId from course where teacherId = " + tempTeacher.getTeacherId();
            resultSet2 = secondStatement.executeQuery(sqlQuery2);
            List<Integer> courses = new ArrayList<>();
            while (resultSet2.next()) {
                courses.add(resultSet2.getInt("courseId"));
            }
            tempTeacher.setCourses(courses);
            teachers.add(tempTeacher);
        }
        return teachers;
    }

    /**
     * Retrieve Method from Teacher by id
     * @param id int
     * @return Teacher
     * @throws SQLException
     */
    @Override
    public Teacher find(Integer id) throws SQLException {
        Teacher tempTeacher = new Teacher();
        String sqlQuery = "select * from teacher where teacherId = " + id;
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        resultSet.next();
        tempTeacher.setTeacherId(resultSet.getInt("teacherId"));
        tempTeacher.setFirstName(resultSet.getString("firstName"));
        tempTeacher.setLastName(resultSet.getString("lastName"));
        String sqlQuery2 = "select courseId from course where teacherId = " + tempTeacher.getTeacherId();
        ResultSet resultSet2 = secondStatement.executeQuery(sqlQuery2);
        List<Integer> courses = new ArrayList<>();
        while (resultSet2.next()) {
            courses.add(resultSet2.getInt("courseId"));
        }
        tempTeacher.setCourses(courses);
        return tempTeacher;
    }

    /**
     * Update Method for Teacher in DB
     * @param oldObject Old Teacher
     * @param newObject New Teacher
     * @return New Teacher
     * @throws SQLException in case of invalid input
     */
    @Override
    public Teacher update(Teacher oldObject, Teacher newObject) throws SQLException {
        if (!oldObject.getCourses().isEmpty()) {
            String sqlQuery2 = "update course set teacherId = -1 where teacherId = " + oldObject.getTeacherId();
            statement.execute(sqlQuery2);
        }

        String sqlQuery = "select * from teacher where teacherId = " + oldObject.getTeacherId();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        resultSet.next();

        if (!Objects.equals(oldObject.getTeacherId(), newObject.getTeacherId())) {
            String sqlQuery2 = "update teacher set teacherId = " + newObject.getTeacherId() + " where teacherId = " + oldObject.getTeacherId();
            statement.execute(sqlQuery2);
        }

        if (!Objects.equals(oldObject.getFirstName(), newObject.getFirstName())) {
            String sqlQuery2 = "update teacher set firstName = '" + newObject.getFirstName() + "' where teacherId = " + newObject.getTeacherId();
            statement.execute(sqlQuery2);
        }

        if (!Objects.equals(oldObject.getLastName(), newObject.getLastName())) {
            String sqlQuery2 = "update teacher set lastName = '" + newObject.getLastName() + "' where teacherId = " + newObject.getTeacherId();
            statement.execute(sqlQuery2);
        }

        if (!newObject.getCourses().isEmpty()) {
            for (Integer courseId :
                    newObject.getCourses()) {
                String sqlQuery2 = "update course set teacherId = " + newObject.getTeacherId() + " where courseId = " + courseId;
                statement.execute(sqlQuery2);
            }
        }

        return newObject;
    }

    /**
     * Delete Method for Teacher in DB
     * @param obj Deleted Teacher
     * @throws SQLException in case of invalid input
     */
    @Override
    public void delete(Teacher obj) throws SQLException {
        String sqlQuery = "delete from teacher where teacherId = " + obj.getTeacherId();
        statement.execute(sqlQuery);
        String sqlQuery2 = "update course set teacherId = -1 where teacherId = " + obj.getTeacherId();
        statement.execute(sqlQuery2);
    }
}

