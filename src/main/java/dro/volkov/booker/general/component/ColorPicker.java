package dro.volkov.booker.general.component;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Tag;

@Tag("input")
public class ColorPicker extends AbstractSinglePropertyField<ColorPicker, String> {

    public ColorPicker() {
        super("value", "", false);
        getElement().setAttribute("type", "color");
        getElement().getClassList().add("app-round");
        setSynchronizedEvent("change");
    }
}
