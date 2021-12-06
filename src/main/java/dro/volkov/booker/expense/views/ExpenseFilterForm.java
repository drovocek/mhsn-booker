package dro.volkov.booker.expense.views;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.service.FilterCrudService;
import dro.volkov.booker.general.view.FilterForm;

@UIScope
@SpringComponent
public class ExpenseFilterForm extends FilterForm<Expense> {

    public ExpenseFilterForm(FilterCrudService<Expense> service) {
        super(service);
    }
}