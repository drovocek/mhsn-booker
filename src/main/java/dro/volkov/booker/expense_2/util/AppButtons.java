package dro.volkov.booker.expense_2.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import dro.volkov.booker.general.event.FormSwitchCommandEvent;

import static dro.volkov.booker.general.event.FormSwitchCommandEvent.FormType.EDIT;
import static dro.volkov.booker.general.event.FormSwitchCommandEvent.FormType.FILTER;

public class AppButtons {

    public static Button editSwitchBtn() {
        Button button = appButton("Edit", VaadinIcon.PLUS, null);
        button.addClickListener(event -> fireToUI(new FormSwitchCommandEvent(button, EDIT)));
        button.addClassName("edit-switch-button");
        return button;
    }

    public static Button filterSwitchBtn() {
        Button button = appButton("Edit", VaadinIcon.FILTER, null);
        button.addClickListener(event -> fireToUI(new FormSwitchCommandEvent(button, FILTER)));
        button.addClassName("filter-switch-button");
        return button;
    }

    public static <T extends Component> void fireToUI(ComponentEvent<? extends T> componentEvent) {
        ComponentUtil.fireEvent(UI.getCurrent(), componentEvent);
    }

    public static Button appButton(String title, VaadinIcon icon, Runnable onClick, ButtonVariant... variants) {
        return new Button() {{
            addThemeVariants(variants);
            if (onClick != null) {
                addClickListener(e -> onClick.run());
            }
            setIcon(new Icon(icon));
            getElement().setAttribute("title", title);
        }};
    }
}
