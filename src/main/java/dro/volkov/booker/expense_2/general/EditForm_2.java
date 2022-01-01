package dro.volkov.booker.expense_2.general;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import dro.volkov.booker.SwipeEvent;
import dro.volkov.booker.SwipeMaster;
import dro.volkov.booker.general.data.entity.HasNew;
import dro.volkov.booker.general.event.FilterPublisher;
import dro.volkov.booker.general.event.OpenEditorNotifier;
import dro.volkov.booker.general.event.SelectNotifier;
import lombok.SneakyThrows;

import java.util.Arrays;

import static dro.volkov.booker.expense_2.util.FormConfigurator.bindFields;

public class EditForm_2<T extends HasNew> extends VerticalLayout
        implements FilterPublisher<T>,
        SelectNotifier<T>, OpenEditorNotifier,
        SwipeMaster {

    protected final H2 title = new H2();

    protected final Class<T> beanType;
    protected final Binder<T> binder;

    protected Registration swipeReg;
    protected Registration selectReg;
    protected Registration openEditorReg;

    public EditForm_2(Class<T> beanType, HasValue<?, ?>... fields) {
        this.beanType = beanType;
        this.binder = new BeanValidationBinder<>(beanType);
        bindFields(binder, fields);
        add(title);
        add(Arrays.stream(fields)
                .map(hasValue -> (Component) hasValue)
                .toArray(Component[]::new));
        configView();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        this.swipeReg = addListener(SwipeEvent.class, event -> {
            if (event.getDirection().equals("right") && isVisible()) {
                close();
            }
        });
        this.selectReg = addUISelectListener(event -> {
            T selected = event.getSelected();
            if (selected == null) {
                close();
            } else {
                open(selected);
            }
        });
        this.openEditorReg = addUIOpenEditorListener(event -> setVisible(!isVisible()));
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        this.swipeReg.remove();
        this.selectReg.remove();
        this.openEditorReg.remove();
    }

    protected void configView() {
        addClassName("edit-form");
        asSwipeEventGenerator(this);
        setWidth("25em");
        close();
    }

    protected void close() {
        setVisible(false);
    }

    protected void open(T entity) {
        this.binder.readBean(entity);
        setVisible(true);
        if (entity.isNew()) {
            title.setText("Add");
        } else {
            title.setText("Edit");
        }
    }

    @SneakyThrows
    protected T getNewInstance() {
        return beanType.getConstructor().newInstance();
    }
}
