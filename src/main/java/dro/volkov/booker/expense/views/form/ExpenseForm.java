package dro.volkov.booker.expense.views.form;

import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.expense.data.entity.Expense;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.Getter;

import java.util.List;

@UIScope
@SpringComponent
public class ExpenseForm extends FormLayout {

    private final BigDecimalField price = new BigDecimalField("Total cost");
    private final DatePicker date = new DatePicker("Date");
    private final TextArea description = new TextArea("Description");
    private final ComboBox<String> category = new ComboBox<>("Category");

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    private final Binder<Expense> binder = new BeanValidationBinder<>(Expense.class);

    private Expense expense;

    public ExpenseForm() {
        addClassName("expense-form");

        binder.bindInstanceFields(this);

        category.setItems(List.of("вар1", "вар2", "вар3"));

        add(price,
                date,
                description,
                category,
                createButtonsLayout());
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
        binder.readBean(expense);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, expense)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(expense);
            fireEvent(new SaveEvent(this, expense));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<ExpenseForm> {

        @Getter
        private Expense expense;

        protected ContactFormEvent(ExpenseForm source, Expense expense) {
            super(source, false);
            this.expense = expense;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ExpenseForm source, Expense expense) {
            super(source, expense);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ExpenseForm source, Expense expense) {
            super(source, expense);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ExpenseForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}