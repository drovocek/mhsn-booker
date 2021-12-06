package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import java.io.Serializable;
import java.util.List;

public interface FilterPublisher<T> extends Serializable {

    default void fireFilterEventToUI(List<T> filtered) {
        ComponentUtil.fireEvent(UI.getCurrent(), new FilterEvent<>((Component) this, filtered));
    }
}
