package dro.volkov.booker.user.view;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dro.volkov.booker.general.service.EntityCrudService;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.general.view.RootGridView;
import dro.volkov.booker.user.entity.User;

@AnonymousAllowed
@Route("registration")
@PageTitle("Registration | Booker")
public class UserRootView extends RootGridView<User> {

    public UserRootView(EntityCrudService<User> service, EditForm<User> form) {
        super(service, form, User.class);
    }

//    private final RegistrationForm registrationForm;
//
//    @PostConstruct
//    private void initView() {
//        addClassName("registration-view");
//        setSizeFull();
////        setAlignItems(Alignment.CENTER);
//        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);
//        setJustifyContentMode(JustifyContentMode.CENTER);
//
//        add(registrationForm);
//    }
}
