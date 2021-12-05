package dro.volkov.booker.user.view;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.MainLayout;
import dro.volkov.booker.general.service.FilterCrudService;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.general.view.RootGridView;
import dro.volkov.booker.security.service.AuthService;
import dro.volkov.booker.user.data.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.security.RolesAllowed;

@UIScope
@RolesAllowed("ROLE_ADMIN")
@Route(value = "users", layout = MainLayout.class)
@PageTitle("Users | Booker")
public class UserRootView extends RootGridView<User> {

    private final AuthService authService;

    @Value("${app.root-user-mail}")
    private String rootUserEmail;

    public UserRootView(@Qualifier("userCrudService") FilterCrudService<User> service,
                        @Qualifier("userEditForm") EditForm<User> form,
                        AuthService authService) {
        super(service, form, User.class);
        this.authService = authService;
    }

    @Override
    protected void configureGrid() {
        super.configureGrid();
        grid.setColumns("enabled", "role", "username", "email", "registrationDate", "active");
    }

    @Override
    protected void saveEntity(EditForm.FormSaveEvent<User> event) {
        User persist = event.getEntity();
        if (persist.isNew()) {
            authService.sendToEmailActivationLink(persist.getEmail());
        }
        super.saveEntity(event);
    }
}
