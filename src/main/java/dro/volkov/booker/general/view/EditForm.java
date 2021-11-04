package dro.volkov.booker.general.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class EditForm<T> extends FormLayout {

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    private final Class<T> beanType;
    private Binder<T> binder;
    private T entity;

    protected void initView() {
        configFields();
        initBinder();
        configView();
    }

    protected void addFields(Component... components) {
        removeAll();
        add(components);
        add(createButtonsLayout());
    }

    protected void configFields() {
    }

    protected void initBinder() {
        binder = new BeanValidationBinder<>(beanType);
        binder.bindInstanceFields(this);
    }

    protected void configView() {
        addClassName("edit-form");
    }

    protected void setEntity(T entity) {
        this.entity = entity;
        binder.readBean(entity);
    }

    protected Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new FormDeleteEvent<>(this, entity)));
        close.addClickListener(event -> fireEvent(new FormCloseEvent<>(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    protected void validateAndSave() {
        try {
            binder.writeBean(entity);
            fireEvent(new FormSaveEvent<>(this, entity));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class EditFormEvent<T> extends ComponentEvent<EditForm<T>> {

        @Getter
        private final T entity;

        protected EditFormEvent(EditForm<T> source, T entity) {
            super(source, false);
            this.entity = entity;
        }
    }

    public static class FormSaveEvent<T> extends EditFormEvent<T> {
        FormSaveEvent(EditForm<T> source, T entity) {
            super(source, entity);
        }
    }

    public static class FormDeleteEvent<T> extends EditFormEvent<T> {
        FormDeleteEvent(EditForm<T> source, T entity) {
            super(source, entity);
        }

    }

    public static class FormCloseEvent<T> extends EditFormEvent<T> {
        FormCloseEvent(EditForm<T> source) {
            super(source, null);
        }
    }

    public <U extends ComponentEvent<?>> Registration addListener(Class<U> eventType, ComponentEventListener<U> listener) {
        return addListener(eventType, listener);
    }
}