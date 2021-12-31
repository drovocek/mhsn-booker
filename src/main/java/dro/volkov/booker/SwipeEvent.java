package dro.volkov.booker;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import lombok.Getter;

@DomEvent("swipe")
public class SwipeEvent extends ComponentEvent<Component> {

    @Getter
    private final String direction;

    public SwipeEvent(Component source,
                      boolean fromClient,
                      @EventData("event.detail.dir") String direction) {
        super(source, fromClient);
        this.direction = direction;
    }
}
