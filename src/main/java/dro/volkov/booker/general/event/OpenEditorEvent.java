package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public class OpenEditorEvent extends ComponentEvent<Component> {

    public OpenEditorEvent(Component source) {
        super(source, false);
    }
}
