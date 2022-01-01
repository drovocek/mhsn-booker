package dro.volkov.booker.expense_2.general;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import dro.volkov.booker.general.data.entity.HasNew;

import static dro.volkov.booker.expense_2.util.GridConfigurator.configureColumns;
import static dro.volkov.booker.expense_2.util.GridConfigurator.configureGrid;

public abstract class RootView_2<T extends HasNew> extends VerticalLayout {

    protected final CustomGrid_2<T> grid;
    protected final FilterForm_2<T> filterForm;
    protected final EditForm_2<T> editForm;
    protected final Class<T> beanType;

    public RootView_2(CustomGrid_2<T> grid,
                      Class<T> beanType) {
        this.grid = grid;
        this.filterForm = getFilter();
        this.editForm = getEditor();
        this.beanType = beanType;
        configView();
    }

    protected abstract FilterForm_2<T> getFilter();

    protected abstract EditForm_2<T> getEditor();

    protected void configView() {
        addClassName("root-grid-view");
        setSizeFull();
        configureColumns(grid, beanType);
        configureGrid(grid);
        add(filterForm, createEntityView());
        grid.getColumns().forEach(col-> System.out.println(col.getKey()));
    }

    protected Component createEntityView() {
        return new HorizontalLayout() {
            {
                addClassNames("content");
                add(grid, editForm);
                setFlexGrow(2, grid);
                setFlexGrow(1, editForm);
                setSizeFull();
            }
        };
    }
}
