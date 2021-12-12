package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

public interface ClosePublisher extends Serializable {

    default void fireCloseEvent() {
        ComponentUtil.fireEvent((Component) this, new CloseEvent((Component) this));
    }

    default void fireUICloseEvent() {
        ComponentUtil.fireEvent(UI.getCurrent(), new CloseEvent((Component) this));
    }
}
