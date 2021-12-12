package dro.volkov.booker.expense.views;

import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.MainLayout;
import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.data.FilterCrudService;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.general.view.FilterForm;
import dro.volkov.booker.general.view.RootView;

import javax.annotation.security.PermitAll;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.Locale;

import static dro.volkov.booker.general.fabric.ComponentFabric.asLabel;

@PermitAll
@UIScope
@Route(value = "expense", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Expense | Booker")
public class ExpenseRootView extends RootView<Expense> {

    public ExpenseRootView(FilterForm<Expense> filterForm,
                           EditForm<Expense> editForm,
                           FilterCrudService<Expense> service) {
        super(filterForm, editForm, service, Expense.class);
    }

    @Override
    protected void updateGridColumns() {
        super.updateGridColumns();
        grid.removeAllColumns();

        grid.addColumn(new NumberRenderer<>(
                        Expense::getPrice, "%(,.2f",
                        Locale.US, "0.00"))
                .setComparator(Comparator.comparing(Expense::getPrice))
                .setHeader("Price");

        grid.addColumn(new ComponentRenderer<>(expense -> asLabel(expense.getCategory())))
                .setComparator(Comparator.comparing(o -> o.getCategory().getName()))
                .setHeader("Category");

        grid.addColumn(new ComponentRenderer<>(expense -> asLabel(expense.getUser())))
                .setComparator(Comparator.comparing(o -> o.getUser().getUsername()))
                .setHeader("Payer");

        grid.addColumn(new LocalDateRenderer<>(
                        Expense::getDate,
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))
                .setComparator(Comparator.comparing(Expense::getDate))
                .setHeader("Date");

        grid.addColumn("description")
                .setHeader("Date");
    }
}


