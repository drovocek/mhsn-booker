package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

public class EditorSwitchEvent extends ComponentEvent<Component> {

    @Getter
    private final boolean opened;

    public EditorSwitchEvent(Component source, boolean opened) {
        super(source, false);
        this.opened = opened;
    }
}
