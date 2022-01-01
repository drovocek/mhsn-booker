package dro.volkov.booker.general.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.SwipeEvent;
import dro.volkov.booker.SwipeMaster;
import dro.volkov.booker.general.data.entity.HasNew;
import dro.volkov.booker.general.event.ClosePublisher;
import dro.volkov.booker.general.event.DeletePublisher;
import dro.volkov.booker.general.event.SavePublisher;
import dro.volkov.booker.general.event.SelectNotifier;
import lombok.SneakyThrows;

import static com.vaadin.flow.component.button.ButtonVariant.*;

public abstract class EditForm<T extends HasNew> extends FormLayout
        implements SavePublisher<T>, DeletePublisher<T>, ClosePublisher,
        SelectNotifier<T>, SwipeMaster {

    protected final Class<T> beanType;
    protected final Binder<T> binder;

    protected T formEntity;
    protected Registration selectRegistration;
    protected Registration swipeRegistration;
    protected H1 title = new H1();

    public EditForm(Class<T> beanType) {
        this.beanType = beanType;
        this.binder = new BeanValidationBinder<>(beanType);
        initView();
    }

    protected void initView() {
        addClassName("edit-form");
        setWidth("25em");
        configFields();
        close();
        asSwipeEventGenerator(this);
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
        swipeRegistration = addListener(SwipeEvent.class, event -> {
            String direction = event.getDirection();
            if (isVisible() && direction.equals("right")) {
                closeAndFireToUI();
            }
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        selectRegistration.remove();
        swipeRegistration.remove();
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
                final Button save = createButton(
                        VaadinIcon.DATABASE,
                        () -> validateAndPushSave(),
                        LUMO_LARGE,
                        LUMO_ICON,
                        LUMO_SUCCESS,
                        LUMO_TERTIARY);
                final Button close = createButton(
                        VaadinIcon.CLOSE,
                        () -> closeAndFireToUI(),
                        LUMO_LARGE,
                        LUMO_ICON,
                        LUMO_CONTRAST);
                final Button delete = createButton(
                        VaadinIcon.TRASH,
                        () -> deleteAndFireToUI(),
                        LUMO_LARGE,
                        LUMO_ICON,
                        LUMO_ERROR,
                        LUMO_TERTIARY);

                save.getElement().setAttribute("title", "Save");
                close.getElement().setAttribute("title", "Close");
                delete.getElement().setAttribute("title", "Delete");

                save.addClickShortcut(Key.ENTER);
                close.addClickShortcut(Key.ESCAPE);

                binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
                add(save, close, delete);
                this.setJustifyContentMode(JustifyContentMode.BETWEEN);
            }
        };
    }

    protected Button createButton(VaadinIcon icon, Runnable onClick, ButtonVariant... variants) {
        return new Button() {{
            addThemeVariants(variants);
            addClickListener(e -> onClick.run());
            setIcon(new Icon(icon));
        }};
    }

    protected void validateAndPushSave() {
        if (binder.writeBeanIfValid(formEntity)) {
            fireUISaveEvent(formEntity);
        }
    }

    protected void closeAndFireToUI() {
        close();
        fireUICloseEvent();
    }

    protected void deleteAndFireToUI() {
        close();
        fireUIDeleteEvent(formEntity);
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