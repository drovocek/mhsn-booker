package dro.volkov.booker.general.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.shared.Registration;

import java.io.Serializable;

public interface FormSwitchCommandNotifier extends Serializable {

    default Registration addUIFormSwitchCommandListener(ComponentEventListener<FormSwitchCommandEvent> listener) {
        if (this instanceof Component) {
            return ComponentUtil.addListener(UI.getCurrent(), FormSwitchCommandEvent.class, listener);
        } else {
            throw new IllegalStateException(String.format("The class '%s' doesn't extend '%s'. Make your implementation for the method '%s'.", this.getClass().getName(), Component.class.getSimpleName(), "addKeyDownListener"));
        }
    }
}
