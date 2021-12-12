package dro.volkov.booker.general.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.general.event.ClosePublisher;
import dro.volkov.booker.general.event.DeletePublisher;
import dro.volkov.booker.general.event.SavePublisher;
import dro.volkov.booker.general.event.SelectNotifier;
import dro.volkov.booker.general.data.entity.HasNewCheck;
import lombok.SneakyThrows;

import static com.vaadin.flow.component.button.ButtonVariant.*;

public abstract class EditForm<T extends HasNewCheck> extends FormLayout
        implements SavePublisher<T>, DeletePublisher<T>, ClosePublisher,
        SelectNotifier<T> {

    protected final Class<T> beanType;
    protected final Binder<T> binder;

    protected T formEntity;
    protected Registration selectRegistration;
    protected H1 title = new H1();

    public EditForm(Class<T> beanType){
        this.beanType = beanType;
        this.binder = new BeanValidationBinder<>(beanType);
        initView();
    }

    protected void initView() {
        addClassName("edit-form");
        setWidth("25em");
        configFields();
        close();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        selectRegistration = addUISelectListener(selectEvent -> {
            T selected = selectEvent.getSelected();
            if (selected == null) {
                close();
            } else {
                open(selected);
            }
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        selectRegistration.remove();
    }

    protected void addFields(Component... components) {
        removeAll();
        add(title);
        add(components);
        add(createButtonsLayout());
        binder.bindInstanceFields(this);
    }

    protected abstract void configFields();

    protected void setFormEntity(T formEntity) {
        this.formEntity = formEntity == null ? getNewInstance() : formEntity;
        binder.readBean(formEntity);
    }

    @SneakyThrows
    protected T getNewInstance() {
        return beanType.getConstructor().newInstance();
    }

    protected Component createButtonsLayout() {
        return new HorizontalLayout() {
            {
                final Button save = new Button("Save");
                final Button delete = new Button("Delete");
                final Button close = new Button("Cancel");

                save.addThemeVariants(LUMO_PRIMARY);
                delete.addThemeVariants(LUMO_ERROR);
                close.addThemeVariants(LUMO_TERTIARY);

                save.addClickShortcut(Key.ENTER);
                close.addClickShortcut(Key.ESCAPE);

                save.addClickListener(event -> validateAndPushSave());
                delete.addClickListener(event -> fireUIDeleteEvent(formEntity));
                close.addClickListener(event -> fireUICloseEvent());

                binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
                add(save, delete, close);
            }
        };
    }

    protected void validateAndPushSave() {
        if (binder.writeBeanIfValid(formEntity)) {
            fireUISaveEvent(formEntity);
            close();
        }
    }

    protected void open(T entity) {
        setFormEntity(entity);
        setVisible(true);
        if (formEntity.isNew()) {
            title.setText("Add");
        } else {
            title.setText("Edit");
        }
    }

    protected void close() {
        setVisible(false);
    }
}