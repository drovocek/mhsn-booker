package dro.volkov.booker.general.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.event.DeleteNotifier;
import dro.volkov.booker.event.FilterPublisher;
import dro.volkov.booker.event.SaveNotifier;
import dro.volkov.booker.event.SelectPublisher;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public abstract class FilterForm<T extends Serializable>
        extends HorizontalLayout
        implements FilterPublisher<T>, SelectPublisher<T>, DeleteNotifier<T>, SaveNotifier<T> {

    private final Class<T> beanType;
    private final Binder<T> binder;
    private final T formEntity;

    protected TextField filterField;
    protected Button addButton;

    private Registration deleteRegistration;
    private Registration saveRegistration;

    public FilterForm(Class<T> beanType) {
        this.beanType = beanType;
        this.binder = new BeanValidationBinder<>(beanType);
        this.formEntity = getNewInstance();
        initView();
    }

    protected void initView() {
        addClassName("toolbar");
        filterField = constructFilterField();
        addButton = constructAddButton();
        add(filterField, addButton);
        binder.bindInstanceFields(this);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        deleteRegistration = addUIDeleteListener(deleteEvent -> pushFilter());
        saveRegistration = addUISaveListener(saveEvent -> pushFilter());
        pushFilter();
    }

    protected void pushFilter() {
        if (binder.writeBeanIfValid(formEntity)) {
            T formEntityClone = SerializationUtils.clone(formEntity);
            fireUIFilterEvent(formEntityClone);
        }
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        deleteRegistration.remove();
        saveRegistration.remove();
    }

    protected TextField constructFilterField() {
        return new TextField() {
            {
                setPlaceholder("Filter by ...");
                setClearButtonVisible(true);
                setValueChangeMode(ValueChangeMode.LAZY);
                addValueChangeListener(e -> pushFilter());
            }
        };
    }

    protected Button constructAddButton() {
        return new Button() {
            {
                setText("Add");
                addClickListener(clickEvent -> fireUISelectEvent(getNewInstance()));
            }
        };
    }

    @SneakyThrows
    protected T getNewInstance() {
        return beanType.getConstructor().newInstance();
    }
}
