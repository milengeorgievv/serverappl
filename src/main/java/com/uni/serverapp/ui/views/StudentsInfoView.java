package com.uni.serverapp.ui.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.uni.serverapp.backend.entity.Student;
import com.uni.serverapp.backend.service.StudentService;

@Route(value = "studentsInfo", layout = MainView.class)
public class StudentsInfoView extends VerticalLayout {
    private Grid<Student> grid;
    private final StudentService studentService;
    private final CallbackDataProvider<Student, Void> dataProvider;
    public StudentsInfoView(StudentService studentService) {
        this.studentService = studentService;
        this.dataProvider = DataProvider.fromCallbacks(
                query -> this.studentService.getAll(
                        query.getOffset(),
                        query.getLimit()
                ).stream(),
                query -> (int) this.studentService.count()
        );
        setSizeFull();
        grid = new Grid<>();
        configureGrid();

        add(grid);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addColumn(Student::getId).setHeader("ID");
        grid.addColumn(Student::getFirstName).setHeader("First Name");
        grid.addColumn(Student::getLastName).setHeader("Last Name");
        grid.addColumn(Student::getCourses).setHeader("Courses");
        grid.setPageSize(20);
        grid.setDataProvider(dataProvider);
    }
}
