package main;

import controller.Controller;
import customexceptions.CustomExceptions;
import repo.CourseRepository;
import repo.StudentRepository;
import repo.TeacherRepository;
import view.Console;
import java.sql.*;


public class Main {


    public static void main(String[] args) throws SQLException, CustomExceptions {
        CourseRepository courseRepository = new CourseRepository();
        StudentRepository studentRepository = new StudentRepository();
        TeacherRepository teacherRepository = new TeacherRepository();

        Controller controller = new Controller(courseRepository, studentRepository, teacherRepository);
        Console console = new Console(controller);
        console.run();

    }
}


