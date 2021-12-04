package dro.volkov.booker.security.view;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.security.service.AuthService;
import dro.volkov.booker.user.data.entity.User;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static dro.volkov.booker.util.NotificationUtil.noticeSSS;

@RequiredArgsConstructor
@UIScope
@SpringComponent
public class ActivationForm extends FormLayout implements BeforeEnterObserver {

    private final H2 title = new H2("Activation");
    private final PasswordField password = new PasswordField("Password");
    private final PasswordField confirmPassword = new PasswordField("Confirm password");
    private final Button submitButton = new Button("submit");

    private final Binder<User> binder = new BeanValidationBinder<>(User.class);
    private User formEntity;

    private final AuthService authService;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Map<String, List<String>> parameters = event.getLocation()
                .getQueryParameters()
                .getParameters();

        if (parameters.containsKey("email")) {
            String email = parameters.get("email").get(0);
            if (authService.activated(email)) {
                event.forwardTo("login");
            } else {
                formEntity = new User();
                formEntity.setEmail(email);
                binder.readBean(formEntity);
            }
        } else {
            event.forwardTo("login");
        }
    }

    @PostConstruct
    private void initView() {
        configFields();
        configBinder();
        configButtons();
        configView();
    }

    private void configView() {
        setMaxWidth("500px");
        setMaxWidth("500px");
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));
        add(title, password, confirmPassword, submitButton);
    }

    private void configFields() {
        setRequiredIndicatorVisible(password, confirmPassword);
        setColspan(title, 2);
        setColspan(password, 2);
        setColspan(confirmPassword, 2);
        setColspan(submitButton, 2);
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    private void configButtons() {
        submitButton.addClickListener(event -> register());
    }

    private void configBinder() {
        binder.forField(confirmPassword)
                .withValidator(confPass -> password.getValue().equals(confPass), "passwords don't match")
                .bind(User::getConfirmPassword, User::setConfirmPassword);

        binder.bindInstanceFields(this);
    }

    private void register() {
        if (binder.writeBeanIfValid(formEntity)) {
            authService.activate(formEntity.getEmail(), formEntity.getPassword());
            noticeSSS("Activation succeeded");
            getUI().ifPresent(ui -> ui.navigate("login"));
        }
    }
}
