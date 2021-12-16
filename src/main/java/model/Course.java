package model;

import java.util.List;
import java.util.Objects;

/**
 * Course Class
 */

public class Course {
    private int id;
    private String name;
    private int teacher;
    private int maxEnrollment;
    private List<Integer> studentsEnrolled;
    private int credits;

    /**
     * Default Constructor
     */
    public Course() {
    }

    /**
     * Constructor without teacher and enrolledStudents
     * to resolve circular dependencies
     *
     * @param id:            id
     * @param name:          String
     * @param maxEnrollment: int - maximum number of Student's
     * @param credits:       int
     */
    public Course(int id, String name, int maxEnrollment, int credits) {
        this.id = id;
        this.name = name;
        this.maxEnrollment = maxEnrollment;
        this.credits = credits;
    }

    /**
     * Course Class
     *
     * @param id:               id
     * @param name:             String
     * @param teacher:          Teacher Object
     * @param maxEnrollment:    int - maximum number of Student's
     * @param studentsEnrolled: List<Student>
     * @param credits:          int
     */

    public Course(int id, String name, int teacher, int maxEnrollment, List<Integer> studentsEnrolled, int credits) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = studentsEnrolled;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacher() {
        return teacher;
    }

    public void setTeacher(int teacher) {
        this.teacher = teacher;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public List<Integer> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void setStudentsEnrolled(List<Integer> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Equals method for comparison
     * Used especially in update() method from InMemoryRepository.java
     *
     * @param o: another Teacher Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(teacher, course.getTeacher()) && Objects.equals(name, course.getName());
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolled=" + studentsEnrolled +
                ", credits=" + credits +
                '}';
    }
}
