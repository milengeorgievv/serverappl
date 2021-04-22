package com.uni.serverapp.ui.views;

import com.uni.serverapp.backend.entity.TopCoursesInt;
import com.uni.serverapp.backend.service.CourseService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;

@Route(value = "mostFamousCourses", layout = MainView.class)
public class MostFamousCoursesView extends VerticalLayout {
    private Grid<TopCoursesInt> grid;
    private final CourseService courseService;
    private final CallbackDataProvider<TopCoursesInt, Void> dataProvider;
    public MostFamousCoursesView(CourseService courseService) {
        this.courseService = courseService;
        this.dataProvider = DataProvider.fromCallbacks(
                query -> this.courseService.getTopCourses().stream()
                .skip(query.getOffset()).limit(query.getLimit()),
                query -> (int) this.courseService.getTopCourses().size()
        );
        setSizeFull();
        grid = new Grid<>();
        configureGrid();

        add(grid);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addColumn(TopCoursesInt::getName).setHeader("Name");
        grid.addColumn(TopCoursesInt::getCredits).setHeader("Credits");
        grid.addColumn(TopCoursesInt::getStudents).setHeader("Students");
        grid.setPageSize(20);
        grid.setDataProvider(dataProvider);
    }

}
