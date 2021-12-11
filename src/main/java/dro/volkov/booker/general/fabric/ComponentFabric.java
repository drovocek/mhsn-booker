package dro.volkov.booker.general.fabric;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.user.data.dict.Role;

public class ComponentFabric {

    public static Component asLabel(Category category) {
        return asLabel(category.getName(), category.getColorHash(), category.getDescription());
    }

    public static Component asLabel(Role role) {
        return asLabel(role.name(), role.getColorHash(), role.role());
    }

    public static Icon asChecked(boolean checked) {
        if (checked) {
            return VaadinIcon.CHECK_SQUARE_O.create();
        }
        return VaadinIcon.THIN_SQUARE.create();
    }

    public static Component asLabel(String text, String colorHash, String title) {
        Label label = new Label(text);
        label.getElement().setAttribute("title", title);
        label.getStyle().set("border-color", colorHash);
        label.getStyle().set("border-width", ".15em");
        label.getStyle().set("border-radius", "10px");
        label.getStyle().set("border-style", "solid");
        label.getStyle().set("display", "inline-block");
        label.getStyle().set("text-align", "center");
        label.getStyle().set("padding", "4px");
        return label;
    }
}
