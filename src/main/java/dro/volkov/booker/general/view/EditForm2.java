package dro.volkov.booker.general.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.event.ClosePublisher;
import dro.volkov.booker.event.DeletePublisher;
import dro.volkov.booker.event.SavePublisher;
import dro.volkov.booker.event.SelectNotifier;
import dro.volkov.booker.expense.data.entity.HasNewCheck;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public abstract class EditForm2<T extends HasNewCheck> extends FormLayout implements SavePublisher<T>, DeletePublisher<T>, ClosePublisher, SelectNotifier<T> {

    private final Class<T> beanType;
    private Binder<T> binder;
    private T formEntity;

    private final H1 title = new H1();

    private Registration selectRegistration;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        selectRegistration = addUISelectListener(selectEvent -> open(selectEvent.getSelected()));
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        selectRegistration.remove();
    }

    protected void initView() {
        configFields();
        initBinder();
        configView();
    }

    protected void addFields(Component... components) {
        removeAll();
        add(title);
        add(components);
        add(createButtonsLayout());
    }

    protected void configFields() {
    }

    protected void initBinder() {
        binder = new BeanValidationBinder<>(beanType);
        binder.bindInstanceFields(this);
    }

    @SneakyThrows
    protected T getNewInstance() {
        return beanType.getConstructor().newInstance();
    }

    protected void configView() {
        addClassName("edit-form");
    }

    protected void setFormEntity(T formEntity) {
        this.formEntity = formEntity == null ? getNewInstance() : formEntity;
        binder.readBean(formEntity);
    }

    protected Component createButtonsLayout() {
        return new HorizontalLayout() {{
            final Button save = new Button("Save");
            final Button delete = new Button("Delete");
            final Button close = new Button("Cancel");

            save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            save.addClickShortcut(Key.ENTER);
            close.addClickShortcut(Key.ESCAPE);

            save.addClickListener(event -> validateAndSave());
            delete.addClickListener(event -> notifyAndDelete());
            close.addClickListener(event -> fireUICloseEvent());

            binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
            add(save, delete, close);
        }};
    }

    protected void validateAndSave() {
        if (binder.writeBeanIfValid(formEntity)) {
            fireUISaveEvent(formEntity);
        }
    }

    protected void notifyAndDelete() {
        //
        fireUIDeleteEvent(formEntity);
    }

    protected void open(T entity) {
        setFormEntity(entity);
        setVisible(entity == null);
        boolean newEntity = entity != null && entity.isNew();
        //delete.setVisible(!newEntity);
        if (newEntity) {
            title.setText("Add");
        } else {
            title.setText("Edit");
        }
    }
}