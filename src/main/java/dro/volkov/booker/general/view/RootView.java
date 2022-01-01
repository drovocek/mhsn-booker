package dro.volkov.booker.general.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import dro.volkov.booker.general.component.CustomGrid;
import dro.volkov.booker.general.data.FilterCrudService;
import dro.volkov.booker.general.data.entity.HasFilterField;
import dro.volkov.booker.general.data.entity.HasNew;

import java.io.Serializable;

public abstract class RootView<T extends HasNew & Serializable & HasFilterField> extends VerticalLayout {

    protected final FilterForm<T> filterForm;
    protected final EditForm<T> editForm;
    protected final FilterCrudService<T> service;
    protected final Class<T> beanType;
    protected final CustomGrid<T> grid;

    public RootView(FilterForm<T> filterForm, EditForm<T> editForm,
                    FilterCrudService<T> service, Class<T> beanType) {
        this.filterForm = filterForm;
        this.editForm = editForm;
        this.service = service;
        this.beanType = beanType;
        this.grid = new CustomGrid<>(service, beanType);
        initView();
    }

    protected void initView() {
        addClassName("root-grid-view");
        setSizeFull();
        updateGridColumns();
        configGrid();
        add(filterForm, createEntityView());
    }

    protected void updateGridColumns() {
    }

    protected void configGrid() {
        grid.getColumns().forEach(col -> {
            col.setAutoWidth(true);
            col.setSortable(true);
            col.setResizable(true);
        });
        grid.setColumnReorderingAllowed(true);
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
