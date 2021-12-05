package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public class DeleteEvent extends ComponentEvent<Component> {

    private Object deleted;

    public DeleteEvent(Component source, Object deleted) {
        super(source, false);
        this.deleted = deleted;
    }
}
