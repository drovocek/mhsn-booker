package dro.volkov.booker.expense.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.view.EditForm;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
public class ExpenseEditForm extends EditForm<Expense> {

    private final BigDecimalField price = new BigDecimalField("Total cost");
    private final DatePicker date = new DatePicker("Date");
    private final TextArea description = new TextArea("Description");
    private final ComboBox<String> category = new ComboBox<>("Category");

    public ExpenseEditForm() {
        super(Expense.class);
    }

    protected void configFields() {
        category.setItems("var1", "var2", "var3");
    }

    @PostConstruct
    public void initView() {
        super.initView();
        addFields(price, category, date, description);
    }
}