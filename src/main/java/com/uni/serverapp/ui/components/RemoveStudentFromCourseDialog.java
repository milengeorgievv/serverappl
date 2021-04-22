package com.uni.serverapp.ui.components;

import com.uni.serverapp.backend.entity.Course;
import com.uni.serverapp.backend.entity.Student;
import com.uni.serverapp.backend.service.CourseService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;

public class RemoveStudentFromCourseDialog extends Dialog {
    private final ComboBox<Student> chosenStudent = new ComboBox<>();
    private final ComboBox<Course> chosenCourse = new ComboBox<>();

    private Course course;
    private H3 dialogTitle = new H3();
    private FormLayout form = new FormLayout();

    private List<Course> listOfCourses;
    private List<Student> listOfStudens;
    private final CourseService courseService;
    
    public RemoveStudentFromCourseDialog(List<Student> students,
                                         CourseService courseService) {
        setMaxWidth("400px");
        this.courseService = courseService;
        listOfCourses = courseService.getAll();
        listOfStudens = students;

        initAddFields();

        add(dialogTitle, form, getActions());
    }

    private void initAddFields() {
        chosenStudent.setLabel("Student");
        chosenStudent.setItems(listOfStudens);
        chosenCourse.setLabel("Course");
        chosenCourse.setItems(listOfCourses);
        form.add(chosenStudent, chosenCourse);
    }

    private HorizontalLayout getActions() {
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.addClickListener(e -> {
            if(chosenCourse.getValue() != null && chosenStudent.getValue() != null) {
                course = chosenCourse.getValue();
                course.removeStudent(chosenStudent.getValue());
                courseService.save(course);
            }
            close();
        });
        cancel.addClickListener(e -> close());
        HorizontalLayout actions = new HorizontalLayout(cancel, save);
        actions.getStyle()
                .set("margin-top", "40px")
                .set("justify-content", "flex-end");

        return actions;
    }
}
