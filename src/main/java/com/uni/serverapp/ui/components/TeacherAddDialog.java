package com.uni.serverapp.ui.components;

import com.uni.serverapp.backend.enums.Title;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.uni.serverapp.backend.entity.Teacher;

import java.util.function.Function;

public class TeacherAddDialog extends Dialog {
    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final ComboBox<Title> title = new ComboBox<>();

    private Teacher teacher;
    private final Function<Teacher, Teacher> saveListener;
    private final H3 dialogTitle = new H3();
    private final FormLayout form = new FormLayout();
    private final Binder<Teacher> formBinder = new Binder<>(Teacher.class);

    public TeacherAddDialog(Function<Teacher, Teacher> saveListener) {
        this.saveListener = saveListener;

        setMaxWidth("400px");

        initAddFields();
        initBindings();

        add(dialogTitle, form, getActions());
    }

    private void initAddFields() {
        firstName.setLabel("First Name");
        lastName.setLabel("Last Name");
        title.setLabel("Title");
        title.setItems(Title.values());
        form.add(firstName, lastName, title);
    }

    private void initBindings() {
        formBinder.bindInstanceFields(this);
    }

    private HorizontalLayout getActions() {
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.addClickListener(e -> {
            if(formBinder.writeBeanIfValid(teacher)) {
                teacher = saveListener.apply(teacher);
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

    public void open(Teacher teacher) {
        setTeacher(teacher);
        open();
    }

    private void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        formBinder.readBean(this.teacher);
        dialogTitle.setText("Add Teacher");
    }
}
