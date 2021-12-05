package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public class CancelEvent extends ComponentEvent<Component> {

    public CancelEvent(Component source) {
        super(source, false);
    }
}
