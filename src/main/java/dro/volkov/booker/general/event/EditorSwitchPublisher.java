package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

public interface EditorSwitchPublisher extends Serializable {

    default void fireUIEditorSwitchEvent(boolean opened) {
        ComponentUtil.fireEvent(UI.getCurrent(), new EditorSwitchEvent((Component) this, opened));
    }
}
