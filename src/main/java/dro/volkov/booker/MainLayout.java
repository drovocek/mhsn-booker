package dro.volkov.booker;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import dro.volkov.booker.category.view.CategoryRootView;
import dro.volkov.booker.expense.views.ExpenseRootView;
import dro.volkov.booker.expense.views.dashboard.DashboardView;
import dro.volkov.booker.security.service.SecurityService;
import dro.volkov.booker.user.data.dict.Role;
import dro.volkov.booker.user.view.UserRootView;

public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        addToNavbar(createHeader());
        addToDrawer(createDrawer());
    }

    private Component createHeader() {
        return new HorizontalLayout() {{
            H1 logo = new H1("Booker");
            logo.addClassNames("text-l", "m-m");
            Button logout = new Button("Log out", e -> securityService.logout());
            add(new DrawerToggle(), logo, logout);

            setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
            expand(logo);
            setWidth("100%");
            addClassNames("py-0", "px-m");
        }};
    }

    private Component createDrawer() {
        return new VerticalLayout() {{
            RouterLink expenseLink = new RouterLink("Expenses", ExpenseRootView.class);
            expenseLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink categoryLink = new RouterLink("Category", CategoryRootView.class);
            categoryLink.setHighlightCondition(HighlightConditions.sameLocation());
            categoryLink.setVisible(securityService.hasRole(Role.ADMIN));

            RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
            dashboardLink.setHighlightCondition(HighlightConditions.sameLocation());
            dashboardLink.setVisible(securityService.hasRole(Role.ADMIN));

            RouterLink usersLink = new RouterLink("Users", UserRootView.class);
            usersLink.setHighlightCondition(HighlightConditions.sameLocation());
            usersLink.setVisible(securityService.hasRole(Role.ADMIN));

            add(
                    expenseLink,
                    categoryLink,
                    dashboardLink,
                    usersLink
            );
        }};
    }
}