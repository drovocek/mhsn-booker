package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public class FilterSwitchEvent extends ComponentEvent<Component> {

    private final boolean opened;

    public FilterSwitchEvent(Component source, boolean opened) {
        super(source, false);
        this.opened = opened;
    }
}
