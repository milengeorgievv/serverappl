package com.uni.serverapp.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.uni.serverapp.backend.entity.Student;

import java.util.function.Function;

public class StudentAddDialog extends Dialog {

    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final IntegerField yearOfStudy = new IntegerField();

    private Student student;
    private final Function<Student, Student> saveListener;
    private final H3 dialogTitle = new H3();
    private final FormLayout form = new FormLayout();
    private final Binder<Student> formBinder = new Binder<>(Student.class);

    public StudentAddDialog(Function<Student, Student> saveListener) {
        this.saveListener = saveListener;

        setMaxWidth("400px");

        initAddFields();
        initBindings();

        add(dialogTitle, form, getActions());
    }

    private void initAddFields() {
        firstName.setLabel("First Name");
        lastName.setLabel("Last Name");
        yearOfStudy.setLabel("Year Of Study");
        form.add(firstName, lastName, yearOfStudy);
    }

    private void initBindings() {
        formBinder.bindInstanceFields(this);
    }

    private HorizontalLayout getActions() {
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.addClickListener(e -> {
            if(formBinder.writeBeanIfValid(student)) {
                student = saveListener.apply(student);
                close();
            }
        });
        cancel.addClickListener(e -> close());
        HorizontalLayout actions = new HorizontalLayout(cancel, save);
        actions.getStyle()
                .set("margin-top", "40px")
                .set("justify-content", "flex-end");

        return actions;
    }

    public void open(Student student) {
        setStudent(student);
        open();
    }

    private void setStudent(Student student) {
        this.student = student;
        formBinder.readBean(this.student);
        dialogTitle.setText("Add Student");
    }
}
