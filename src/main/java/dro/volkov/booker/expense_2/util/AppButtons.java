package dro.volkov.booker.expense_2.util;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import dro.volkov.booker.general.event.FormSwitchCommandEvent;

import static com.vaadin.flow.component.button.ButtonVariant.*;
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

    public static Button saveBtn(Runnable onClick) {
        Button button = appButton("Save", VaadinIcon.DATABASE, onClick,
                LUMO_LARGE,
                LUMO_ICON,
                LUMO_SUCCESS,
                LUMO_TERTIARY);
        button.addClassName("save-button");
        button.addClickShortcut(Key.ENTER);
        return button;
    }

    public static Button closeBtn(Runnable onClick) {
        Button button = appButton("Close", VaadinIcon.CLOSE, onClick,
                LUMO_LARGE,
                LUMO_ICON,
                LUMO_CONTRAST,
                LUMO_TERTIARY);
        button.addClassName("close-button");
        button.addClickShortcut(Key.ESCAPE);
        return button;
    }

    public static Button deleteBtn(Runnable onClick) {
        Button button = appButton("Delete", VaadinIcon.TRASH, onClick,
                LUMO_LARGE,
                LUMO_ICON,
                LUMO_ERROR,
                LUMO_TERTIARY);
        button.addClassName("delete-button");
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
