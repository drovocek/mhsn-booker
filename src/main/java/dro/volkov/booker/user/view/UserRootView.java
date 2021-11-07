package dro.volkov.booker.user.view;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.MainLayout;
import dro.volkov.booker.general.service.FilterCrudService;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.general.view.RootGridView;
import dro.volkov.booker.user.data.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.security.PermitAll;

@PermitAll
@UIScope
@Route(value = "users", layout = MainLayout.class)
@PageTitle("Users | Booker")
public class UserRootView extends RootGridView<User> {

    public UserRootView(@Qualifier("userCrudService") FilterCrudService<User> service,
                        @Qualifier("userEditForm") EditForm<User> form) {
        super(service, form, User.class);
    }

    @Override
    protected void configureGrid() {
        super.configureGrid();
        grid.setColumns("enabled", "role", "username", "email", "registrationDate", "active");
    }
}
