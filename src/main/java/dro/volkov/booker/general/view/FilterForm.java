package dro.volkov.booker.general.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.general.event.DeleteNotifier;
import dro.volkov.booker.general.event.FilterPublisher;
import dro.volkov.booker.general.event.SaveNotifier;
import dro.volkov.booker.general.event.SelectPublisher;
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
        add(filterField);
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

    @SneakyThrows
    protected T getNewInstance() {
        return beanType.getConstructor().newInstance();
    }
}
