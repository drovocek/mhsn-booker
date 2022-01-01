package dro.volkov.booker.expense_2.general;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.SwipeEvent;
import dro.volkov.booker.SwipeMaster;
import dro.volkov.booker.general.event.FilterPublisher;
import dro.volkov.booker.general.event.OpenFilterNotifier;
import lombok.SneakyThrows;

import java.util.Arrays;

import static dro.volkov.booker.expense_2.util.FormConfigurator.bindFields;

public class FilterForm_2<T> extends HorizontalLayout
        implements FilterPublisher<T>,
        OpenFilterNotifier,
        SwipeMaster {

    protected final Class<T> beanType;
    protected final Binder<T> binder;

    protected Registration swipeReg;
    protected Registration openFilterReg;

    public FilterForm_2(Class<T> beanType, HasValue<?, ?>... fields) {
        this.beanType = beanType;
        this.binder = new BeanValidationBinder<>(beanType);
        bindFields(binder, fields);
        add(Arrays.stream(fields)
                .map(hasValue -> (Component) hasValue)
                .toArray(Component[]::new));
        configView();
    }

    protected void configView() {
        addClassName("filter-Form");
        asSwipeEventGenerator(this);
        close();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        this.swipeReg = addListener(SwipeEvent.class, event -> {
            if (event.getDirection().equals("up") && isVisible()) {
                close();
            }
        });
        this.openFilterReg = addUIOpenFilterListener(event -> setVisible(!isVisible()));
        pushFilter();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        this.swipeReg.remove();
    }

    protected void pushFilter() {
        T formBean = getNewInstance();
        if (binder.writeBeanIfValid(formBean)) {
            fireUIFilterEvent(formBean);
        }
    }

    protected void close() {
        setVisible(false);
    }

    @SneakyThrows
    protected T getNewInstance() {
        return beanType.getConstructor().newInstance();
    }
}
