package dro.volkov.booker.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

import java.util.List;

@Getter
public class FilterEvent<T> extends ComponentEvent<Component> {

    private final List<T> filtered;

    public FilterEvent(Component source, List<T> filtered) {
        super(source, false);
        this.filtered = filtered;
    }
}
