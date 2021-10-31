package com.example.application.expense.views;

import com.example.application.MainLayout;
import com.example.application.expense.data.entity.Expense;
import com.example.application.expense.data.service.ExpenseService;
import com.example.application.expense.views.form.ExpenseForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

@RequiredArgsConstructor
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Expense | Vaadin")
public class ExpenseView extends VerticalLayout {

    private final Grid<Expense> grid = new Grid<>(Expense.class);

    private final TextField filterText = new TextField();

    private final ExpenseService service;

    private final ExpenseForm form;

    @PostConstruct
    private void initView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
//        grid.setColumns("firstName", "lastName", "email");
//        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
//        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addContact());

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

    public void editContact(Expense expense) {
        if (expense == null) {
            closeEditor();
        } else {
            form.setExpense(expense);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setExpense(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Expense());
    }

    private void configureForm() {
        form.setWidth("25em");
        form.addListener(ExpenseForm.SaveEvent.class, this::saveContact);
        form.addListener(ExpenseForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ExpenseForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveContact(ExpenseForm.SaveEvent event) {
        service.saveExpense(event.getExpense());
        updateList();
        closeEditor();
    }

    private void deleteContact(ExpenseForm.DeleteEvent event) {
        service.deleteExpense(event.getExpense());
        updateList();
        closeEditor();
    }
}
