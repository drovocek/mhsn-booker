package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public class CloseEvent extends ComponentEvent<Component> {

    public CloseEvent(Component source) {
        super(source, false);
    }
}
