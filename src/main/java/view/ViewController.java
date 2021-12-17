package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Course;
import model.Student;
import model.Teacher;
import repo.CourseRepository;
import repo.StudentRepository;
import repo.TeacherRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ViewController {
    private Controller controller;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private CourseRepository courseRepository;
    public static Student tempStudent;
    public static Teacher tempTeacher;

    public ViewController() throws SQLException {
        studentRepository = new StudentRepository();
        teacherRepository = new TeacherRepository();
        courseRepository = new CourseRepository();
        controller = new Controller(courseRepository, studentRepository, teacherRepository);
    }

    @FXML
    private javafx.scene.control.TextField courseIdTextField;

    @FXML
    private javafx.scene.control.ListView<Course> showCoursesListView;

    @FXML
    private javafx.scene.control.ListView<Course> showTeacherCoursesListView;

    @FXML
    private javafx.scene.control.ListView<Integer> showEnrolledCoursesListView;

    @FXML
    private javafx.scene.control.ListView<Student> showStudentsListView;

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private javafx.scene.control.Label textLabel;

    @FXML
    private javafx.scene.control.TextField idTextBox;

    @FXML
    private javafx.scene.control.ChoiceBox choiceBox;

    @FXML
    private javafx.scene.control.TextField teacherCourseIdTextField;

    public void renderStudentWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("student-view.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Student " + tempStudent.getFirstName() + " " + tempStudent.getLastName());
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void renderTeacherWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("teacher-view.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Teacher " + tempTeacher.getFirstName() + " " + tempTeacher.getLastName());
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    public void logInButtonClicked() throws SQLException, IOException {
        try {
            int id = Integer.parseInt(idTextBox.getText());
            if (Objects.equals(choiceBox.getValue().toString(), "Student")) {
                tempStudent = controller.findStudentById(id);
                renderStudentWindow();
            } else {
                tempTeacher = controller.findTeacherById(id);
                renderTeacherWindow();
            }
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid input!");
            errorAlert.setContentText("Not good input!");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void onSeeCreditsButtonClicked() {
        int credits = tempStudent.getTotalCredits();
        textLabel.setText(Integer.toString(credits));

    }

    @FXML
    public void closeButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onShowCoursesButtonClicked() throws SQLException {
        ListView<Course> courseListView = new ListView<>();
        for (Course c :
                controller.getCourses()) {
            courseListView.getItems().add(c);
        }
        showCoursesListView.setItems(courseListView.getItems());
    }

    @FXML
    public void onShowEnrolledCoursesButtonClicked() {
        ListView<Integer> courseListView = new ListView<>();
        for (Integer c :
                tempStudent.getEnrolledCourses()) {
            courseListView.getItems().add(c);
        }

        showEnrolledCoursesListView.setItems(courseListView.getItems());
    }

    @FXML
    public void onEnrollStudentButtonClicked() {
        try {
            int courseId = Integer.parseInt(courseIdTextField.getText());
            List<Integer> courses = tempStudent.getEnrolledCourses();
            courses.add(courseId);
            controller.updateStudent(tempStudent.getStudentId(), tempStudent.getFirstName(), tempStudent.getLastName(), tempStudent.getStudentId(), tempStudent.getTotalCredits(), courses);
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid input!");
            errorAlert.setContentText("Not good input!");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void onSeeCoursesButtonClicked() throws SQLException {
        ListView<Course> courseListView = new ListView<>();
        for (Integer c :
                tempTeacher.getCourses()) {
            courseListView.getItems().add(controller.findCourseById(c));
        }
        showTeacherCoursesListView.setItems(courseListView.getItems());
    }

    @FXML
    public void onSeeEnrolledStudentsButtonClicked() {
        try {
            int courseId = Integer.parseInt(teacherCourseIdTextField.getText());
            ListView<Student> stundentListView = new ListView<>();
            for (Integer s : controller.findCourseById(courseId).getStudentsEnrolled()) {
                stundentListView.getItems().add(controller.findStudentById(s));
            }
            showStudentsListView.setItems(stundentListView.getItems());
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid input!");
            errorAlert.setContentText("Not good input!");
            errorAlert.showAndWait();
        }
    }
}