package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public class FilterEvent<T> extends ComponentEvent<Component> {

    private final T filter;

    public FilterEvent(Component source, T filter) {
        super(source, false);
        this.filter = filter;
    }
}
