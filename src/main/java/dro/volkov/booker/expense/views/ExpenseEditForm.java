package dro.volkov.booker.expense.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.category.data.CategoryCrudService;
import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.fabric.ComponentFabric;
import dro.volkov.booker.general.view.EditForm;

import java.time.LocalDate;
import java.time.ZoneId;

@UIScope
@SpringComponent
public class ExpenseEditForm extends EditForm<Expense> {

    private final CategoryCrudService categoryCrudService;

    private NumberField price;
    private DatePicker date;
    private TextArea description;
    private ComboBox<Category> category;

    public ExpenseEditForm(CategoryCrudService categoryCrudService) {
        super(Expense.class);
        this.categoryCrudService = categoryCrudService;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        category.setItems(categoryCrudService.getAll());
    }

    @Override
    protected void configFields() {
        this.price = new NumberField("Total cost");
        this.date = new DatePicker("Date");
        this.description = new TextArea("Description");
        this.category = constructCategory();
        addFields(price, category, date, description);
    }

    private ComboBox<Category> constructCategory() {
        return new ComboBox<>() {{
            setLabel("Category");
            setItemLabelGenerator(Category::getName);
            setRenderer(new ComponentRenderer<>(ComponentFabric::asLabel));
        }};
    }

    @Override
    protected void open(Expense entity) {
        super.open(entity);
        if (entity.isNew()) {
            price.setValue(1.0);
            date.setValue(LocalDate.now(ZoneId.of("Europe/Moscow")));
            description.setValue("some staff");
        }
    }
}