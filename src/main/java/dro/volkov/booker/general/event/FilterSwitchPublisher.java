package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

public interface FilterSwitchPublisher extends Serializable {

    default void fireUIFilterSwitchEvent(boolean opened) {
        ComponentUtil.fireEvent(UI.getCurrent(), new FilterSwitchEvent((Component) this, opened));
    }
}
