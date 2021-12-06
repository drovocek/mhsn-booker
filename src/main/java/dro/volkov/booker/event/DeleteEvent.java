package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public class DeleteEvent<T> extends ComponentEvent<Component> {

    private final T deleted;

    public DeleteEvent(Component source, T deleted) {
        super(source, false);
        this.deleted = deleted;
    }
}
