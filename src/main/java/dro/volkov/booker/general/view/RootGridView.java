package dro.volkov.booker.general.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import dro.volkov.booker.general.service.EntityCrudService;
import dro.volkov.booker.general.view.EditForm.FormCloseEvent;
import dro.volkov.booker.general.view.EditForm.FormDeleteEvent;
import dro.volkov.booker.general.view.EditForm.FormSaveEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public abstract class RootGridView<T> extends VerticalLayout {

    private final EntityCrudService<T> service;

    private final EditForm<T> form;

    private final Class<T> beanType;

    private final TextField filterText = new TextField();

    private Grid<T> grid;

    @PostConstruct
    private void initView() {
        addClassName("root-grid-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid = new Grid<>(beanType);
        grid.addClassNames("filter-crud-grid");
        grid.setSizeFull();
//        grid.setColumns("firstName", "lastName", "email");
//        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
//        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addEntity());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(service.findByFilter(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    public void editContact(T entity) {
        if (entity == null) {
            closeEditor();
        } else {
            form.setEntity(entity);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setEntity(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    @SneakyThrows
    private void addEntity() {
        grid.asSingleSelect().clear();
        editContact(beanType.newInstance());
    }

    private void configureForm() {
        form.setWidth("25em");
        form.addListener(FormSaveEvent.class, (ComponentEventListener) e -> saveEntity((FormSaveEvent<T>) e));
        form.addListener(FormDeleteEvent.class, (ComponentEventListener) e -> deleteEntity((FormDeleteEvent<T>) e));
        form.addListener(FormCloseEvent.class, (ComponentEventListener) e -> closeEditor());
    }

    private void saveEntity(FormSaveEvent<T> event) {
        service.save(event.getEntity());
        updateList();
        closeEditor();
    }

    private void deleteEntity(FormDeleteEvent<T> event) {
        service.delete(event.getEntity());
        updateList();
        closeEditor();
    }
}
