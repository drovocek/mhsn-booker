package dro.volkov.booker.category.view;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.general.component.ColorPicker;
import dro.volkov.booker.general.view.EditForm;

@UIScope
@SpringComponent
public class CategoryEditForm extends EditForm<Category> {

    private TextField name;
    private ColorPicker colorHash;
    private TextArea description;

    public CategoryEditForm() {
        super(Category.class);
    }

    @Override
    protected void configFields() {
        this.name = new TextField("Name");
        this.colorHash = new ColorPicker();
        this.description = new TextArea("Description");
        addFields(name, colorHash, description);
    }
}