package dro.volkov.booker.expense.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.category.data.CategoryCrudService;
import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.fabric.ComponentFabric;
import dro.volkov.booker.general.view.EditForm;

@UIScope
@SpringComponent
public class ExpenseEditForm extends EditForm<Expense> {

    private final CategoryCrudService categoryCrudService;

    private BigDecimalField price;
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
        this.price = new BigDecimalField("Total cost");
        this.date = new DatePicker("Date");
        this.description = new TextArea("Description");
        this.category = constructCategory();
        addFields(price, category, date, description);
    }

    private ComboBox<Category> constructCategory(){
        return new ComboBox<>(){{
            setLabel("Category");
            setItemLabelGenerator(Category::getName);
            setRenderer(new ComponentRenderer<>(ComponentFabric::asLabel));
        }};
    }
}