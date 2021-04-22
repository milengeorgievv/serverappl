package com.uni.serverapp.ui.views;

import com.uni.serverapp.backend.entity.CombinedInt;
import com.uni.serverapp.backend.service.StudentService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;

@Route(value = "studentsCredits", layout = MainView.class)
public class StudentsCreditsView extends VerticalLayout {
    private Grid<CombinedInt> grid;
    private final StudentService studentService;
    private final CallbackDataProvider<CombinedInt, Void> dataProvider;
    public StudentsCreditsView(StudentService studentService) {
        this.studentService = studentService;
        this.dataProvider = DataProvider.fromCallbacks(
                query -> this.studentService.getAllStudentsWithCredits()
                        .stream().skip(query.getOffset()).limit(query.getLimit()),
                query -> (int) this.studentService.getAllStudentsWithCredits().size()
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
        grid.addColumn(CombinedInt::getCredits).setHeader("Credits");
        grid.setPageSize(20);
        grid.setDataProvider(dataProvider);
    }
}
