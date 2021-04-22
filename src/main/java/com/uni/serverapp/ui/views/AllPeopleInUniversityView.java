package com.uni.serverapp.ui.views;

import com.uni.serverapp.backend.entity.CombinedInt;
import com.uni.serverapp.backend.entity.Course;
import com.uni.serverapp.backend.service.CourseService;
import com.uni.serverapp.ui.components.*;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.uni.serverapp.backend.entity.Student;
import com.uni.serverapp.backend.entity.Teacher;
import com.uni.serverapp.backend.service.StudentService;
import com.uni.serverapp.backend.service.TeacherService;

@Route(value = "allPeopleInUniversity", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class AllPeopleInUniversityView extends VerticalLayout {
    private Grid<CombinedInt> grid;
    private final MenuBar actionsMenu;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final ConfirmationDialog confirmationDialog;
    private final StudentAddDialog studentAddDialog;
    private final TeacherAddDialog teacherAddDialog;
    private final CourseAddDialog courseAddDialog;
    private final AddStudentToCourseDialog addStudentToCourseDialog;
    private final RemoveStudentFromCourseDialog removeStudentFromCourseDialog;
    private final CallbackDataProvider<CombinedInt, Void> dataProvider;
    public AllPeopleInUniversityView(TeacherService teacherService,
                                     StudentService studentService,
                                     CourseService courseService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.dataProvider = DataProvider.fromCallbacks(
                query -> this.teacherService.getAllPeople().stream()
                        .skip(query.getOffset()).limit(query.getLimit()),
                query -> (int) this.teacherService.getAllPeople().size()
        );
        setSizeFull();

        actionsMenu = new MenuBar();
        initActionsMenu();

        grid = new Grid<>();
        configureGrid();

        studentAddDialog = new StudentAddDialog(
                student -> {
                    Student addedStudent = this.studentService.save(student);
                    dataProvider.refreshAll();

                    return addedStudent;
                }
        );
        teacherAddDialog = new TeacherAddDialog(
                teacher -> {
                    Teacher addedTeacher = this.teacherService.save(teacher);
                    dataProvider.refreshAll();

                    return addedTeacher;
                }
        );
        courseAddDialog = new CourseAddDialog(
                course -> {
                    Course addedCourse = this.courseService.save(course);
                    dataProvider.refreshAll();
                    return addedCourse;
                },
                teacherService.getAll()
        );
        addStudentToCourseDialog = new AddStudentToCourseDialog(
                studentService.getAll(),
                courseService
        );
        removeStudentFromCourseDialog = new RemoveStudentFromCourseDialog(
                studentService.getAll(),
                courseService
        );
        
        this.confirmationDialog = new ConfirmationDialog();

        add(actionsMenu, grid);
    }

    private void initActionsMenu() {
        MenuItem addStudent = actionsMenu.addItem("Add Student");
        addStudent.addClickListener(e -> studentAddDialog.open(new Student()));
        MenuItem addTeacher = actionsMenu.addItem("Add Teacher");
        addTeacher.addClickListener(e -> teacherAddDialog.open(new Teacher()));
        MenuItem addCourse = actionsMenu.addItem("Add Course");
        addCourse.addClickListener(e -> courseAddDialog.open(new Course()));
        MenuItem addStudentToCourse = actionsMenu.addItem("Add Student To Course");
        addStudentToCourse.addClickListener(e -> addStudentToCourseDialog.open());
        MenuItem removeStudentFromCourse = actionsMenu.addItem("Remove Student From Course");
        removeStudentFromCourse.addClickListener(e -> removeStudentFromCourseDialog.open());
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addColumn(CombinedInt::getFirstName).setHeader("First Name");
        grid.addColumn(CombinedInt::getLastName).setHeader("Last Name");
        grid.addColumn(CombinedInt::getCredits).setHeader("Courses/Year of Study");

        grid.setPageSize(20);
        grid.setDataProvider(dataProvider);
    }
}
