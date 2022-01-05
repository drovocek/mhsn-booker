package dro.volkov.booker.expense_2.general;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.SwipeEvent;
import dro.volkov.booker.SwipeMaster;
import dro.volkov.booker.general.data.entity.HasNew;
import dro.volkov.booker.general.event.*;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Optional;

import static com.vaadin.flow.component.button.ButtonVariant.*;
import static dro.volkov.booker.expense_2.util.AppButtons.appButton;
import static dro.volkov.booker.expense_2.util.FormConfigurator.bindFields;
import static dro.volkov.booker.general.event.FormSwitchCommandEvent.FormType.EDIT;

public class EditForm_2<T extends HasNew> extends VerticalLayout
        implements FilterPublisher<T>, SavePublisher<T>, DeletePublisher<T>, EditorSwitchPublisher,
        SelectNotifier<T>, FormSwitchCommandNotifier, FilterSwitchNotifier,
        SwipeMaster {

    protected final H2 title = new H2();

    protected final Class<T> beanType;
    protected final Binder<T> binder;
    protected T formBean;

    protected Registration swipeReg;
    protected Registration selectReg;
    protected Registration filterSwitchReg;
    protected Registration formSwitchCommandReg;

    public EditForm_2(Class<T> beanType, HasValue<?, ?>... fields) {
        this.beanType = beanType;
        this.binder = new BeanValidationBinder<>(beanType);
        bindFields(this.binder, fields);
        add(this.title);
        add(Arrays.stream(fields)
                .map(hasValue -> (Component) hasValue)
                .toArray(Component[]::new));
        add(createButtonsLayout());
        configView();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        this.selectReg = addUISelectListener(event -> {
            Optional
                    .ofNullable(event.getSelected())
                    .ifPresentOrElse(selected -> {
                                fillForm(selected);
                                switchOpened(true);
                            },
                            () -> switchOpened(false));
        });
        this.formSwitchCommandReg = addUIFormSwitchCommandListener(event -> {
            if (event.getFormType().equals(EDIT)) {
                switchOpened(!isVisible());
            }
        });
        this.filterSwitchReg = addUIFilterSwitchListener(event -> {
            if (event.isOpened()) {
                switchOpened(false);
            }
        });
        this.swipeReg = addListener(SwipeEvent.class, event -> {
            if (event.getDirection().equals("right") && isVisible()) {
                switchOpened(false);
            }
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        this.selectReg.remove();
        this.formSwitchCommandReg.remove();
        this.filterSwitchReg.remove();
        this.swipeReg.remove();
    }

    protected void configView() {
        addClassName("edit-form");
        asSwipeEventGenerator(this);
        setWidth("25em");
        switchOpened(false);
    }

    protected void fillForm(T entity) {
        if (entity == null) {
            entity = getNewInstance();
        }
        this.formBean = entity;
        this.binder.readBean(this.formBean);
        this.title.setText(entity.isNew() ? "Add" : "Edit");
    }

    protected Component createButtonsLayout() {
        return new HorizontalLayout() {

            final Button delete;
            private Registration editorSwitchReg;

            @Override
            protected void onAttach(AttachEvent attachEvent) {
                super.onAttach(attachEvent);
                this.editorSwitchReg = addUIEditorSwitchListener(event -> delete.setVisible(event.getSelected() != null));
            }

            private Registration addUIEditorSwitchListener(ComponentEventListener<SelectEvent<T>> listener) {
                return ComponentUtil.addListener(UI.getCurrent(), SelectEvent.class, (ComponentEventListener) listener);
            }

            @Override
            protected void onDetach(DetachEvent detachEvent) {
                super.onDetach(detachEvent);
                this.editorSwitchReg.remove();
            }

            {
                final Button save = appButton(
                        "Save",
                        VaadinIcon.DATABASE,
                        () -> validateAndPushSave(),
                        LUMO_LARGE,
                        LUMO_ICON,
                        LUMO_SUCCESS,
                        LUMO_TERTIARY);
                final Button close = appButton(
                        "Close",
                        VaadinIcon.CLOSE,
                        () -> clearGridSelect(),
                        LUMO_LARGE,
                        LUMO_ICON,
                        LUMO_CONTRAST);
                this.delete = appButton(
                        "Delete",
                        VaadinIcon.TRASH,
                        () -> fireUIDeleteEvent(EditForm_2.this.formBean),
                        LUMO_LARGE,
                        LUMO_ICON,
                        LUMO_ERROR,
                        LUMO_TERTIARY);

                save.addClickShortcut(Key.ENTER);
                close.addClickShortcut(Key.ESCAPE);

                EditForm_2.this.binder.addStatusChangeListener(e -> save.setEnabled(EditForm_2.this.binder.isValid()));
                add(save, close, delete);
                this.setJustifyContentMode(JustifyContentMode.BETWEEN);
            }
        };
    }

    protected void validateAndPushSave() {
        if (binder.writeBeanIfValid(formBean)) {
            fireUISaveEvent(formBean);
        }
    }

    protected void clearGridSelect() {
        fireUIDeleteEvent(null);
    }

    protected void switchOpened(boolean opened) {
        setVisible(opened);
        fireUIEditorSwitchEvent(opened);
    }

    @SneakyThrows
    protected T getNewInstance() {
        return this.beanType.getConstructor().newInstance();
    }
}
