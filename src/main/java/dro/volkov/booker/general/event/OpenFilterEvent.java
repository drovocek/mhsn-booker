package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public class OpenFilterEvent extends ComponentEvent<Component> {

    public OpenFilterEvent(Component source) {
        super(source, false);
    }
}
