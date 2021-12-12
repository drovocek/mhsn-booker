package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

public interface FilterPublisher<T> extends Serializable {

    default void fireFilterEvent(T filter) {
        ComponentUtil.fireEvent((Component) this, new FilterEvent<>((Component) this, filter));
    }

    default void fireUIFilterEvent(T filter) {
        ComponentUtil.fireEvent(UI.getCurrent(), new FilterEvent<>((Component) this, filter));
    }
}
