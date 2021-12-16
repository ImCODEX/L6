package model;

import java.util.List;
import java.util.Objects;

/**
 * Teacher Class
 */

public class Teacher extends Person{
    private int teacherId;
    private List<Integer> courses;

    /**
     * Default Constructor
     */
    public Teacher() {
        super();
    }

    /**
     * Teacher Constructor
     * @param firstName: String
     * @param lastName: String
     * @param courses: List<Course>
     */
    public Teacher(String firstName, String lastName, int teacherId, List<Integer> courses) {
        super(firstName, lastName);
        this.teacherId = teacherId;
        this.courses = courses;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public void setCourses(List<Integer> courses) {
        this.courses = courses;
    }

    /**
     * Equals method for comparison
     * Used especially in update() method from InMemoryRepository.java
     * @param o: another Teacher Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o){
        if (this == o ) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getFirstName(), teacher.getFirstName()) && Objects.equals(getLastName(), teacher.getLastName());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", courses=" + courses +
                '}';
    }
}
