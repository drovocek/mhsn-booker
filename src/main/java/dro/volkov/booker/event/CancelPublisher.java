package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;

import java.io.Serializable;

public interface CancelPublisher extends Serializable {

    default void fireCancelEventTo(Component target) {
        ComponentUtil.fireEvent(target, new CancelEvent((Component) this));
    }
}
