package com.uni.serverapp.ui.components;

import com.uni.serverapp.backend.entity.Teacher;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.uni.serverapp.backend.entity.Course;

import java.util.List;
import java.util.function.Function;

public class CourseAddDialog extends Dialog {
    private final TextField name = new TextField();
    private final IntegerField credits = new IntegerField();
    private final ComboBox<Teacher> teacher = new ComboBox<>();

    private Course course;
    //private final TeacherService teacherService;
    private final Function<Course, Course> saveListener;
    private final H3 dialogTitle = new H3();
    private final FormLayout form = new FormLayout();
    private final Binder<Course> formBinder = new Binder<>(Course.class);
    //private final CallbackDataProvider<Teacher, Void> dataProvider;
    private List<Teacher> listOfTeachers;

    public CourseAddDialog(Function<Course, Course> saveListener,
                           List<Teacher> teachers) {
        this.saveListener = saveListener;
        setMaxWidth("400px");
        listOfTeachers = teachers;
        initAddFields();
        initBindings();
        add(dialogTitle, form, getActions());
    }


    private void initAddFields() {
        name.setLabel("Description");
        credits.setLabel("Credits");
        teacher.setLabel("Teacher Of This Course");
        teacher.setItems(listOfTeachers);
        form.add(name, credits, teacher);
    }

    private void initBindings() {
        formBinder.bindInstanceFields(this);
    }

    private HorizontalLayout getActions() {
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.addClickListener(e -> {
            if(formBinder.writeBeanIfValid(course)) {
                course = saveListener.apply(course);
                close();
            }
        });
        //teacherForCourse.setDataProvider();
        cancel.addClickListener(e -> close());
        HorizontalLayout actions = new HorizontalLayout(cancel, save);
        actions.getStyle()
                .set("margin-top", "40px")
                .set("justify-content", "flex-end");

        return actions;
    }

    public void open(Course course) {
        setCourse(course);
        open();
    }

    private void setCourse(Course course) {
        this.course = course;
        formBinder.readBean(this.course);
        dialogTitle.setText("Add Course");
    }
}
