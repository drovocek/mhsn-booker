package dro.volkov.booker.security.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@AnonymousAllowed
@UIScope
@Route("activation")
@PageTitle("Activation | Booker")
public class ActivationView extends VerticalLayout {

    private final ActivationForm activationForm;

    @PostConstruct
    private void initView() {
        addClassName("activationForm-view");
        setSizeFull();
//        setAlignItems(Alignment.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER, activationForm);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(activationForm);
    }
}
