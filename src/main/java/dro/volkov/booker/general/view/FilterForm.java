package dro.volkov.booker.general.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import dro.volkov.booker.event.FilterPublisher;
import dro.volkov.booker.event.SelectPublisher;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@RequiredArgsConstructor
public abstract class FilterForm<T extends Serializable> extends HorizontalLayout implements FilterPublisher<T>, SelectPublisher<T> {

    private final Class<T> beanType;
    private Binder<T> binder;
    private T formEntity;

    protected TextField filterField;
    protected Button addButton;

    @PostConstruct
    protected void initView() {
        addClassName("toolbar");
        filterField = constructFilterField();
        addButton = constructAddButton();
        initBinder();
        add(filterField, addButton);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        pushFilter();
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
        return new Button() {{
            setText("Add");
            addClickListener(clickEvent -> fireUISelectEvent(null));
        }};
    }

    protected void pushFilter(){
        System.out.println("PUSH");
        if (binder.writeBeanIfValid(formEntity)) {
            System.out.println("PUSH2");
            T formEntityClone = SerializationUtils.clone(formEntity);
            fireUIFilterEvent(formEntityClone);
        }
    }

    @SneakyThrows
    protected void initBinder() {
        formEntity = beanType.getDeclaredConstructor().newInstance();
        binder = new BeanValidationBinder<>(beanType);
        binder.bindInstanceFields(this);
    }
}
