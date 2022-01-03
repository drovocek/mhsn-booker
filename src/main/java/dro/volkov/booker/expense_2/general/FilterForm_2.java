package dro.volkov.booker.expense_2.general;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.SwipeEvent;
import dro.volkov.booker.SwipeMaster;
import dro.volkov.booker.general.event.FilterPublisher;
import dro.volkov.booker.general.event.OpenFilterNotifier;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.stream.Stream;

import static dro.volkov.booker.expense_2.util.FormConfigurator.bindFields;

public class FilterForm_2<T> extends FormLayout
        implements FilterPublisher<T>,
        OpenFilterNotifier,
        SwipeMaster {

    protected final H2 title = new H2("Filter");

    protected final Class<T> beanType;
    protected final Binder<T> binder;

    protected final HasValue<?, ?>[] fields;

    protected Registration swipeReg;
    protected Registration openFilterReg;

    public FilterForm_2(Class<T> beanType, HasValue<?, ?>... fields) {
        this.beanType = beanType;
        this.binder = new BeanValidationBinder<>(beanType);
        this.fields = fields;
        bindFields(binder, fields);
        add(title);
        add(Arrays.stream(fields)
                .map(hasValue -> (Component) hasValue)
                .peek(field -> field.getElement().getClassList().add("filter-field"))
                .toArray(Component[]::new));
        configView();
    }

    protected void configView() {
        addClassName("filter-form");
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
        clear();
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

    protected void clear() {
        Stream.of(fields).forEach(HasValue::clear);
    }

    protected void close() {
        setVisible(false);
    }

    @SneakyThrows
    protected T getNewInstance() {
        return beanType.getConstructor().newInstance();
    }
}
