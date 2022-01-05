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
import dro.volkov.booker.general.event.EditorSwitchNotifier;
import dro.volkov.booker.general.event.FilterPublisher;
import dro.volkov.booker.general.event.FilterSwitchPublisher;
import dro.volkov.booker.general.event.FormSwitchCommandNotifier;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.stream.Stream;

import static dro.volkov.booker.expense_2.util.FormConfigurator.bindFields;
import static dro.volkov.booker.general.event.FormSwitchCommandEvent.FormType.FILTER;

public class FilterForm_2<T> extends FormLayout
        implements FilterPublisher<T>, FilterSwitchPublisher,
        FormSwitchCommandNotifier, EditorSwitchNotifier,
        SwipeMaster {

    protected final H2 title = new H2("Filter");

    protected final Class<T> beanType;
    protected final Binder<T> binder;

    protected final HasValue<?, ?>[] fields;

    protected Registration swipeReg;
    protected Registration filterSwitchReg;
    protected Registration editorSwitchReg;
    protected Registration formSwitchCommandReg;

    public FilterForm_2(Class<T> beanType, HasValue<?, ?>... fields) {
        this.beanType = beanType;
        this.binder = new BeanValidationBinder<>(beanType);
        this.fields = fields;
        bindFields(this.binder, fields);
        add(this.title);
        add(Arrays.stream(fields)
                .map(hasValue -> (Component) hasValue)
                .peek(field -> field.getElement().getClassList().add("filter-field"))
                .toArray(Component[]::new));
        setColspan(this.title, fields.length);
        setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", fields.length)
        );
        configView();
    }

    protected void configView() {
        addClassName("filter-form");
        asSwipeEventGenerator(this);
        switchOpened(false);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        this.formSwitchCommandReg = addUIFormSwitchCommandListener(event -> {
            if (event.getFormType().equals(FILTER)) {
                switchOpened(!isVisible());
            }
        });
        this.editorSwitchReg = addUIEditorSwitchListener(event -> {
            if (event.isOpened()) {
                switchOpened(false);
            }
        });
        this.swipeReg = addListener(SwipeEvent.class, event -> {
            if (event.getDirection().equals("up") && isVisible()) {
                switchOpened(false);
            }
        });
        clear();
        pushFilter();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        this.formSwitchCommandReg.remove();
        this.editorSwitchReg.remove();
        this.swipeReg.remove();
    }

    protected void pushFilter() {
        T formBean = getNewInstance();
        if (this.binder.writeBeanIfValid(formBean)) {
            fireUIFilterEvent(formBean);
        }
    }

    protected void clear() {
        Stream.of(this.fields).forEach(HasValue::clear);
    }

    protected void switchOpened(boolean opened) {
        setVisible(opened);
        fireUIFilterSwitchEvent(opened);
    }

    @SneakyThrows
    protected T getNewInstance() {
        return this.beanType.getConstructor().newInstance();
    }
}
