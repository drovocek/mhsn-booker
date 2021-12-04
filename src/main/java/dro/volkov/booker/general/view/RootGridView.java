package dro.volkov.booker.general.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import dro.volkov.booker.general.service.FilterCrudService;
import dro.volkov.booker.general.view.EditForm.FormCloseEvent;
import dro.volkov.booker.general.view.EditForm.FormDeleteEvent;
import dro.volkov.booker.general.view.EditForm.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;

import static dro.volkov.booker.util.NotificationUtil.noticeSSS;

@RequiredArgsConstructor
public abstract class RootGridView<T> extends VerticalLayout {

    protected final FilterCrudService<T> service;

    protected final EditForm<T> form;

    protected final Class<T> beanType;

    protected final TextField filterText = new TextField();

    protected Grid<T> grid;

    @PostConstruct
    protected void initView() {
        addClassName("root-grid-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getEntity());
        updateList();
        form.close();
    }

    protected void configureGrid() {
        grid = new Grid<>(beanType);
        grid.addClassNames("filter-crud-grid");
        grid.setSizeFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editEntity(event.getValue()));
    }

    protected HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add");
        addContactButton.addClickListener(click -> addEntity());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    protected void updateList() {
        grid.setItems(service.findByFilter(filterText.getValue()));
    }

    protected Component getEntity() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    protected void editEntity(T entity) {
        if (entity == null) {
            form.close();
        } else {
            form.open(entity);
            form.asEditForm(true);
        }
    }

    @SneakyThrows
    protected void addEntity() {
        grid.asSingleSelect().clear();
        editEntity(beanType.newInstance());
        form.asEditForm(false);
    }

    protected void configureForm() {
        form.setWidth("25em");
        form.addListener(FormSaveEvent.class, (ComponentEventListener) e -> saveEntity((FormSaveEvent<T>) e));
        form.addListener(FormDeleteEvent.class, (ComponentEventListener) e -> deleteEntity((FormDeleteEvent<T>) e));
        form.addListener(FormCloseEvent.class, (ComponentEventListener) e -> removeClassName("editing"));
        form.addListener(FormOpenEvent.class, (ComponentEventListener) e -> addClassName("editing"));
    }

    protected void saveEntity(FormSaveEvent<T> event) {
        service.save(event.getEntity());
        updateList();
        form.close();
        noticeSSS("Save succeeded");
    }

    protected void deleteEntity(FormDeleteEvent<T> event) {
        service.delete(event.getEntity());
        updateList();
        form.close();
        noticeSSS("Delete succeeded");
    }
}
