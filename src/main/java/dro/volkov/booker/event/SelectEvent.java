package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class SelectEvent<T> extends ComponentEvent<Component> {

    private final T selected;

    public SelectEvent(Component source, T selected) {
        super(source, false);
        this.selected = selected;
    }

    public T getSelected() {
        return selected;
    }
}
