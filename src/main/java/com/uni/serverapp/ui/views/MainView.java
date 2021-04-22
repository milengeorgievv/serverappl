package com.uni.serverapp.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLink;

import java.util.HashMap;
import java.util.Map;

public class MainView extends AppLayout implements BeforeEnterObserver {
    private final Tabs tabs = new Tabs();
    private final Map<Class, Tab> navigationTargetToTab
            = new HashMap<>();

    public MainView() {
        addMenuTab("All People In University", AllPeopleInUniversityView.class);
        addMenuTab("All Students", StudentsInfoView.class);
        addMenuTab("All Students' Credits", StudentsCreditsView.class);
        addMenuTab("All Teachers", TeachersView.class);
        addMenuTab("Most Famous Courses", MostFamousCoursesView.class);
        addMenuTab("Teachers With Most Students", BestTeachersView.class);
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        addToNavbar(tabs);
    }

    private void addMenuTab(String label, Class<? extends Component> target) {
        Tab tab = new Tab(new RouterLink(label, target));
        navigationTargetToTab.put(target, tab);
        tabs.add(tab);
    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
    }
}
