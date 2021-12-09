package dro.volkov.booker.expense.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.view.EditForm2;

@UIScope
@SpringComponent
public class ExpenseEditForm2 extends EditForm2<Expense> {

    private BigDecimalField price;
    private DatePicker date;
    private TextArea description;
    private ComboBox<String> category;

    public ExpenseEditForm2() {
        super(Expense.class);
    }

    @Override
    protected void configFields() {
        super.configFields();
        this.price = new BigDecimalField("Total cost");
        this.date = new DatePicker("Date");
        this.description = new TextArea("Description");
        this.category = new ComboBox<>("Category");
        category.setItems("var1", "var2", "var3");
        addFields(price, category, date, description);
    }
}