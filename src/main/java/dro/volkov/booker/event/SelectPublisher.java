package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

public interface SelectPublisher<T> extends Serializable {

    default void fireSelectEventToUI(T selected) {
        ComponentUtil.fireEvent(UI.getCurrent(), new SelectEvent<>((Component) this, selected));
    }
}
