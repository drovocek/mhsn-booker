package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

public interface SelectPublisher<T> extends Serializable {

    default void fireSelectEvent(T selected) {
        ComponentUtil.fireEvent((Component) this, new SelectEvent<>((Component) this, selected));
    }

    default void fireUISelectEvent(T selected) {
        ComponentUtil.fireEvent(UI.getCurrent(), new SelectEvent<>((Component) this, selected));
    }
}
