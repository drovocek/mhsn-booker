package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

public interface DeletePublisher<T> extends Serializable {

    default void fireDeleteEvent(T deleted) {
        ComponentUtil.fireEvent((Component) this, new DeleteEvent<>((Component) this, deleted));
    }

    default void fireUIDeleteEvent(T deleted) {
        ComponentUtil.fireEvent(UI.getCurrent(), new DeleteEvent<>((Component) this, deleted));
    }
}
