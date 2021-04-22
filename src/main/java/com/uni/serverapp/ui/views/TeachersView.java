package com.uni.serverapp.ui.views;

import com.uni.serverapp.backend.entity.TeacherInt;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.uni.serverapp.backend.service.TeacherService;

@Route(value = "teachers", layout = MainView.class)
public class TeachersView extends VerticalLayout {
    private Grid<TeacherInt> grid;
    private final TeacherService teacherService;
    private final CallbackDataProvider<TeacherInt, Void> dataProvider;
    public TeachersView(TeacherService teacherService) {
        this.teacherService = teacherService;
        this.dataProvider = DataProvider.fromCallbacks(
                query -> this.teacherService.getAllTeachersWithCoursesAndStudents().
                        stream().skip(query.getOffset()).limit(query.getLimit()),
                query -> (int) this.teacherService.getAllTeachersWithCoursesAndStudents().size()
        );
        setSizeFull();
        grid = new Grid<>();
        configureGrid();

        add(grid);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addColumn(TeacherInt::getFirstName).setHeader("First Name");
        grid.addColumn(TeacherInt::getLastName).setHeader("Last Name");
        grid.addColumn(TeacherInt::getName).setHeader("Course Name");
        grid.addColumn(TeacherInt::getStudents).setHeader("Students");
        grid.setPageSize(20);
        grid.setDataProvider(dataProvider);
    }
}
