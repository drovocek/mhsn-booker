package dro.volkov.booker.security.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@AnonymousAllowed
@Route("registration")
@PageTitle("Registration | Booker")
public class RegistrationView extends VerticalLayout {

    private final RegistrationForm registrationForm;

    @PostConstruct
    private void initView() {
        addClassName("registration-view");
        setSizeFull();
//        setAlignItems(Alignment.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(registrationForm);
    }
}
