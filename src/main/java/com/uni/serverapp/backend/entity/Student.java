package com.uni.serverapp.backend.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student extends AbstractEntity {

    @NotNull
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "year_of_study", nullable = true)
    @Nullable
    private Integer yearOfStudy;

    @ManyToMany(mappedBy = "students",
            fetch = FetchType.EAGER/*,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH}*/)
    /*@JoinTable(name = "course_student",
                joinColumns = @JoinColumn(name = "student_id"),
                inverseJoinColumns = @JoinColumn(name = "course_id"))*/
    private Set<Course> courses = new HashSet<Course>();

    public Student() {

    }

    public Student(String firstName, String lastName, Integer yearOfStudy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfStudy = yearOfStudy;
    }

    public void addCourse(Course course) {
        for(Course c : this.courses) {
            if(c.equals(course)) {
                return;
            }
        }
        this.courses.add(course);
        course.getStudents().add(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

