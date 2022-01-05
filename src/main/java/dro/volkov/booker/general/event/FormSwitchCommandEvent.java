package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

public class FormSwitchCommandEvent extends ComponentEvent<Component> {

    @Getter
    private final FormType formType;

    public FormSwitchCommandEvent(Component source, FormType formType) {
        super(source, false);
        this.formType = formType;
    }

    public enum FormType {
        EDIT, FILTER
    }
}
