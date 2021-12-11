package dro.volkov.booker.category.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.general.view.FilterForm;

@UIScope
@SpringComponent
public class CategoryFilterForm extends FilterForm<Category> {

    public CategoryFilterForm() {
        super(Category.class);
    }
}