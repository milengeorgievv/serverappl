package com.uni.serverapp.ui.views;

import com.uni.serverapp.backend.entity.CombinedInt;
import com.uni.serverapp.backend.service.TeacherService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;

@Route(value = "bestTeachers", layout = MainView.class)
public class BestTeachersView extends VerticalLayout {
    private Grid<CombinedInt> grid;
    private final TeacherService teacherService;
    private final CallbackDataProvider<CombinedInt, Void> dataProvider;
    public BestTeachersView(TeacherService teacherService) {
        this.teacherService = teacherService;
        this.dataProvider = DataProvider.fromCallbacks(
                query -> this.teacherService.getTopTeachers().stream()
                .skip(query.getOffset()).limit(query.getLimit()),
                query -> (int) this.teacherService.getTopTeachers().size()
        );
        setSizeFull();
        grid = new Grid<>();
        configureGrid();

        add(grid);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addColumn(CombinedInt::getFirstName).setHeader("First Name");
        grid.addColumn(CombinedInt::getLastName).setHeader("Last Name");
        grid.addColumn(CombinedInt::getCredits).setHeader("Students");
        grid.setPageSize(20);
        grid.setDataProvider(dataProvider);
    }
}
