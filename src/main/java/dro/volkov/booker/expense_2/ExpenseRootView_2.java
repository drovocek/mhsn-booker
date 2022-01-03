package dro.volkov.booker.expense_2;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.MainLayout;
import dro.volkov.booker.expense_2.general.EditForm_2;
import dro.volkov.booker.expense_2.general.FilterForm_2;
import dro.volkov.booker.expense_2.general.RootView_2;

import javax.annotation.security.PermitAll;

import static dro.volkov.booker.expense_2.util.AppFields.*;

@PermitAll
@UIScope
@Route(value = "expense_2", layout = MainLayout.class)
@RouteAlias(value = "2", layout = MainLayout.class)
@PageTitle("Expense | Booker")
public class ExpenseRootView_2 extends RootView_2<Expense_2, ExpenseFilter> {

    public ExpenseRootView_2(DataService<Expense_2> dataService) {
        super(dataService, Expense_2.class);
    }

    @Override
    protected FilterForm_2<ExpenseFilter> getFilter() {
        return new FilterForm_2<>(ExpenseFilter.class,
                createCheckBox("Category", "category", "categoryCrudService_2"),
                createNumberField("Price", "price"),
                createDatePicker("Date", "date"),
                createTextField("Description", "description")
        );
    }

    @Override
    protected EditForm_2<Expense_2> getEditor() {
        return new EditForm_2<>(Expense_2.class,
                createCheckBox("Category", "category", "categoryCrudService_2"),
                createNumberField("Price", "price"),
                createDatePicker("Date", "date"),
                createTextArea("Description", "description")
        );
    }
}


