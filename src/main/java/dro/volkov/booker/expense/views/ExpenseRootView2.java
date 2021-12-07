package dro.volkov.booker.expense.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.MainLayout;
import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.service.FilterCrudService;
import dro.volkov.booker.general.view.CustomGrid;
import dro.volkov.booker.general.view.EditForm2;
import dro.volkov.booker.general.view.FilterForm;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

@RequiredArgsConstructor
@PermitAll
@UIScope
@Route(value = "trt", layout = MainLayout.class)
@RouteAlias(value = "343", layout = MainLayout.class)
@PageTitle("Expense2 | Booker2")
public class ExpenseRootView2 extends HorizontalLayout {

    private final FilterForm<Expense> filterForm;
    private final EditForm2<Expense> editForm;
    private final FilterCrudService<Expense> service;

    private CustomGrid<Expense> gridView;

    @PostConstruct
    public void initView() {
        gridView = new CustomGrid<>(service, Expense.class);
        add(new VerticalLayout(filterForm, getContentView()));
    }

    protected Component getContentView() {
        return new HorizontalLayout() {{
            setFlexGrow(2, gridView);
            setFlexGrow(1, editForm);
            addClassNames("content");
            setSizeFull();
            add(gridView, editForm);
        }};
    }
}


