package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

public interface SavePublisher<T> extends Serializable {

    default void fireSaveEvent(T persistent) {
        ComponentUtil.fireEvent((Component) this, new SaveEvent<>((Component) this, persistent));
    }

    default void fireUISaveEvent(T persistent) {
        ComponentUtil.fireEvent(UI.getCurrent(), new SaveEvent<>((Component) this, persistent));
    }
}
