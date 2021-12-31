package dro.volkov.booker;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import dro.volkov.booker.category.view.CategoryRootView;
import dro.volkov.booker.dashboard.view.DashboardView;
import dro.volkov.booker.expense.views.ExpenseRootView;
import dro.volkov.booker.security.service.SecurityService;
import dro.volkov.booker.user.view.UserRootView;

import static dro.volkov.booker.util.NotificationUtil.noticeSSS;

@JavaScript("./src/swipe-behavior.js")
public class MainLayout extends AppLayout implements SwiperMaster {

    private final SecurityService securityService;
    private boolean drawerClosed = false;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        DrawerToggle toggle = new DrawerToggle();
        configView(toggle);
        addToDrawer(getTitle(), getRouteTabs());
        addToNavbar(true, toggle, getActionTabs());

    }

    private void configView(DrawerToggle toggle) {
        asSwipeEventGenerator(this);
        addListener(SwipeEvent.class, event -> {
            if (drawerClosed && event.getDirection().equals("right") ||
                    !drawerClosed && event.getDirection().equals("left")) {
                toggle.clickInClient();
                drawerClosed = !drawerClosed;
            }
        });
    }

    private H2 getTitle() {
        return new H2() {{
            setText("Booker");
            getStyle()
                    .set("font-size", "var(--lumo-font-size-l)")
                    .set("line-height", "var(--lumo-size-l)")
                    .set("margin", "0 var(--lumo-space-m)");
        }};
    }

    private Tabs getActionTabs() {
        return new Tabs() {{
            add(
                    createActionTab(VaadinIcon.PLUS, "Add/Edit", () -> noticeSSS("Open Edit")),
                    createActionTab(VaadinIcon.FILTER, "Filter", () -> noticeSSS("Open Filter"))
            );
            addThemeVariants(TabsVariant.LUMO_MINIMAL, TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        }};
    }

    private Tabs getRouteTabs() {
        return new Tabs() {{
            add(
                    createRouteTab(VaadinIcon.MONEY, "Expenses", ExpenseRootView.class),
                    createRouteTab(VaadinIcon.HASH, "Category", CategoryRootView.class),
                    createRouteTab(VaadinIcon.LINE_CHART, "Dashboard", DashboardView.class),
                    createRouteTab(VaadinIcon.USER, "Customers", UserRootView.class),
                    createActionTab(VaadinIcon.OUT, "Log out", securityService::logout)
            );
            setOrientation(Tabs.Orientation.VERTICAL);
        }};
    }

    private Tab createRouteTab(VaadinIcon viewIcon, String viewName, Class<? extends Component> navTarget) {
        return new Tab() {{
            Icon icon = viewIcon.create();
            icon.addClassNames("menu-bar-icon");
            RouterLink link = new RouterLink();
            link.add(icon, new Span(viewName));
            link.setRoute(navTarget);
            link.setTabIndex(-1);
            add(link);
        }};
    }

    private Tab createActionTab(VaadinIcon viewIcon, String viewName, Runnable action) {
        return new Tab() {{
            Icon icon = viewIcon.create();
            icon.addClassNames("menu-bar-icon");
            Div cover = new Div(icon, new Span(viewName));
            cover.addClickListener(event -> action.run());
            add(cover);
        }};
    }
}