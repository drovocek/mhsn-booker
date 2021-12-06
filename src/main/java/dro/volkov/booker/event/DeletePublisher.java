package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

public interface DeletePublisher<T> extends Serializable {

    default void fireDeleteEventToUI(T deleted) {
        ComponentUtil.fireEvent(UI.getCurrent(), new DeleteEvent<>((Component) this, deleted));
    }
}
