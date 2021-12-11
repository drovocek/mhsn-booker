package dro.volkov.booker.user.view;

import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.MainLayout;
import dro.volkov.booker.general.data.FilterCrudService;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.general.view.FilterForm;
import dro.volkov.booker.general.view.RootView;
import dro.volkov.booker.user.data.entity.User;

import javax.annotation.security.RolesAllowed;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;

import static dro.volkov.booker.general.fabric.ComponentFabric.asChecked;
import static dro.volkov.booker.general.fabric.ComponentFabric.asLabel;

@UIScope
@RolesAllowed("ROLE_ADMIN")
@Route(value = "users", layout = MainLayout.class)
@PageTitle("Users | Booker")
public class UserRootView extends RootView<User> {

    protected UserRootView(FilterForm<User> filterForm,
                           EditForm<User> editForm,
                           FilterCrudService<User> service) {
        super(filterForm, editForm, service, User.class);
    }

    @Override
    protected void updateGridColumns() {
        super.updateGridColumns();
        grid.removeAllColumns();

        grid.addColumn(new ComponentRenderer<>(user -> asChecked(user.isActive())))
                .setComparator(Comparator.comparing(User::getRole))
                .setHeader("Active");

        grid.addColumn(new ComponentRenderer<>(user -> asChecked(user.isEnabled())))
                .setComparator(Comparator.comparing(User::getRole))
                .setHeader("Enabled");

        grid.addColumn("username")
                .setHeader("Username");

        grid.addColumn("email")
                .setHeader("Email");

        grid.addColumn(new ComponentRenderer<>(user -> asLabel(user.getRole())))
                .setComparator(Comparator.comparing(User::getRole))
                .setHeader("Category");

        grid.addColumn(new LocalDateTimeRenderer<>(
                User::getLastAccess,
                DateTimeFormatter
                        .ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM)
                        .withZone(ZoneId.of("Europe/Moscow"))))
                .setComparator(Comparator.comparing(User::getLastAccess))
                .setHeader("Last access");

        grid.addColumn(new LocalDateTimeRenderer<>(
                        User::getRegistration,
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)))
                .setComparator(Comparator.comparing(User::getRegistration))
                .setHeader("Registration");
    }
}
