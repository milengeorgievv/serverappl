package com.uni.serverapp.backend.entity;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course extends AbstractEntity implements Cloneable {

    @NotNull
    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private Integer credits;

    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students = new HashSet<Student>();

    public Course() {

    }

    public Course(String name, Integer credits, Teacher teacher) {
        this.name = name;
        this.credits = credits;
        this.teacher = teacher;
    }

    public Course(String name, Integer credits, Teacher teacher, Set<Student> students) {
        this.name = name;
        this.credits = credits;
        this.teacher = teacher;
        this.students = students;
    }

    public void addStudent(Student student) {
        for(Student st : this.students) {
            if(st.equals(student)) {
                return ;
            }
        }
        this.students.add(student);
        student.getCourses().add(this);
    }
    
    public void removeStudent(Student student) {
        boolean flag = false;
        for(Student st : this.students) {
            if(st.equals(student)) {
                flag = true;
            }
        }
        if(flag) {
            this.students.remove(student);
            student.getCourses().remove(this);
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return name;
    }
}

