package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

public class SelectEvent<T> extends ComponentEvent<Component> {

    @Getter
    private final T selected;

    public SelectEvent(Component source, T selected) {
        super(source, false);
        this.selected = selected;
    }
}
