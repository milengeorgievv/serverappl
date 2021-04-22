package com.uni.serverapp.backend.entity;

import com.uni.serverapp.backend.enums.Title;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher extends AbstractEntity {

    @NotNull
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "title")
    private Title title;

    @OneToMany(mappedBy = "teacher",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Course> courses;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, Title title) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
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

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + title;
    }
}

