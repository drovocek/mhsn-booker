package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public class SaveEvent extends ComponentEvent<Component> {

    private Object persist;

    public SaveEvent(Component source, Object persist) {
        super(source, false);
        this.persist = persist;
    }
}
