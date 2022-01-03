package dro.volkov.booker.expense_2.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import dro.volkov.booker.general.event.OpenEditorEvent;
import dro.volkov.booker.general.event.OpenFilterEvent;

import static dro.volkov.booker.util.NotificationUtil.noticeSSS;

public class AppButtons {

    public static Button editSwitchBtn() {
        Button button = createButton(
                "Edit",
                VaadinIcon.PLUS,
                () -> noticeSSS("Open Edit"));
        button.addClickListener(event -> fireToUI(new OpenEditorEvent(button)));
        button.addClassName("edit-switch-button");
        return button;
    }

    public static Button filterSwitchBtn() {
        Button button = createButton(
                "Edit",
                VaadinIcon.FILTER,
                () -> noticeSSS("Open Filter"));
        button.addClickListener(event -> fireToUI(new OpenFilterEvent(button)));
        button.addClassName("filter-switch-button");
        return button;
    }

    public static <T extends Component> void fireToUI(ComponentEvent<? extends T> componentEvent) {
        ComponentUtil.fireEvent(UI.getCurrent(), componentEvent);
    }

    public static Button createButton(String title, VaadinIcon icon, Runnable onClick, ButtonVariant... variants) {
        return new Button() {{
            addThemeVariants(variants);
            addClickListener(e -> onClick.run());
            setIcon(new Icon(icon));
            getElement().setAttribute("title", title);
        }};
    }
}
