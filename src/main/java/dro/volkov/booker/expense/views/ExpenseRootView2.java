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
import dro.volkov.booker.general.view.CustomGrid;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.general.view.FilterForm;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

@RequiredArgsConstructor
@PermitAll
@UIScope
@Route(value = "expenses", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Expense | Booker")
public class ExpenseRootView2 extends HorizontalLayout {

    private final FilterForm<Expense> filterForm;
    private final CustomGrid<Expense> gridView;
    private final EditForm<Expense> editForm;

    @PostConstruct
    public void initView() {
        add(new VerticalLayout(filterForm, getContentView()));
    }

    protected Component getContentView() {
        HorizontalLayout content = new HorizontalLayout(gridView, editForm);
        content.setFlexGrow(2, gridView);
        content.setFlexGrow(1, editForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
}


