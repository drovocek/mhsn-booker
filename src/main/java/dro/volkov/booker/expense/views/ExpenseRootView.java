package dro.volkov.booker.expense.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.MainLayout;
import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.service.FilterCrudService;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.general.view.RootGridView;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.security.PermitAll;

@PermitAll
@UIScope
@Route(value = "expenses", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Expense | Booker")
public class ExpenseRootView extends RootGridView<Expense> {

    public ExpenseRootView(@Qualifier("expenseCrudService") FilterCrudService<Expense> service,
                           @Qualifier("expenseEditForm") EditForm<Expense> form) {
        super(service, form, Expense.class);
    }

    @Override
    protected void configureGrid() {
        super.configureGrid();
        grid.setColumns("category", "price", "date", "username", "description");
    }
}
