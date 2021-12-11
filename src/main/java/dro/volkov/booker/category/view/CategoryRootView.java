package dro.volkov.booker.category.view;

import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.MainLayout;
import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.general.data.FilterCrudService;
import dro.volkov.booker.general.fabric.ComponentFabric;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.general.view.FilterForm;
import dro.volkov.booker.general.view.RootView;

import javax.annotation.security.RolesAllowed;
import java.util.Comparator;

@RolesAllowed("ROLE_ADMIN")
@UIScope
@Route(value = "category", layout = MainLayout.class)
@PageTitle("Category | Booker")
public class CategoryRootView extends RootView<Category> {

    public CategoryRootView(FilterForm<Category> filterForm,
                            EditForm<Category> editForm,
                            FilterCrudService<Category> service) {
        super(filterForm, editForm, service, Category.class);
    }

    @Override
    protected void updateGridColumns() {
        super.updateGridColumns();
        grid.removeAllColumns();
        grid.addColumn(new ComponentRenderer<>(ComponentFabric::asLabel))
                .setComparator(Comparator.comparing(Category::getName))
                .setHeader("Category");
    }
}


