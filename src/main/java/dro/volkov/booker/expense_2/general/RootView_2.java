package dro.volkov.booker.expense_2.general;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import dro.volkov.booker.expense_2.CustomGrid_2;
import dro.volkov.booker.expense_2.DataService;
import dro.volkov.booker.general.data.entity.HasNew;

import static dro.volkov.booker.expense_2.util.GridConfigurator.configureColumns;
import static dro.volkov.booker.expense_2.util.GridConfigurator.configureGrid;

public abstract class RootView_2<T extends HasNew, F> extends VerticalLayout {

    protected final CustomGrid_2<T> grid;
    protected final FilterForm_2<F> filterForm;
    protected final EditForm_2<T> editForm;

    protected final DataService<T> dataService;

    protected final Class<T> beanType;

    public RootView_2(DataService<T> dataService,
                      Class<T> beanType) {
        this.grid = new CustomGrid_2<>(dataService);
        this.filterForm = getFilter();
        this.editForm = getEditor();
        this.dataService = dataService;
        this.beanType = beanType;
        configView();
    }

    protected abstract FilterForm_2<F> getFilter();

    protected abstract EditForm_2<T> getEditor();

    protected void configView() {
        addClassName("root-grid-view");
        setSizeFull();
        configureColumns(this.grid, this.beanType);
        configureGrid(this.grid);
        add(this.filterForm, createEntityView());
    }

    protected Component createEntityView() {
        return new HorizontalLayout() {
            {
                addClassNames("content");
                add(RootView_2.this.grid, RootView_2.this.editForm);
                setFlexGrow(2, RootView_2.this.grid);
                setFlexGrow(1, RootView_2.this.editForm);
                setSizeFull();
            }
        };
    }
}
