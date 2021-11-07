package dro.volkov.booker.security.view;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class RegistrationForm extends FormLayout {

////    private final SecurityService authService;
//
//    private final H2 title = new H2("Registration");
//    private final PasswordField password = new PasswordField("Password");
//    private final PasswordField confirmPassword = new PasswordField("Confirm password");
//    private final Button submitButton = new Button("submit");
//
//    private Binder<User> binder=new BeanValidationBinder<>(User.class);
//    private User formEntity;
//
////    public RegistrationForm() {
////        super(User.class);
////    }
////
////    @PostConstruct
////    public void initView() {
////        super.initView();
////        addFields(title, email, password, confirmPassword);
////    }
//
//    @PostConstruct
//    private void initView() {
//        configFields();
//        configBinder();
//        configButtons();
//        configView();
//    }
//
//    @Override
//    protected void onAttach(AttachEvent attachEvent) {
//        super.onAttach(attachEvent);
//        formEntity = new User();
//        binder.readBean(formEntity);
//    }
//
//    private void configView() {
//        setMaxWidth("500px");
//        setMaxWidth("500px");
//        setResponsiveSteps(
//                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
//                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));
//        add(title, email, password, confirmPassword, submitButton);
//    }
//
//    private void configFields() {
//        setRequiredIndicatorVisible(email, password, confirmPassword);
//        setColspan(title, 2);
//        setColspan(email, 2);
//        setColspan(password, 2);
//        setColspan(confirmPassword, 2);
//        setColspan(submitButton, 2);
//    }
//
//    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
//        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
//    }
//
//    private void configButtons() {
//        submitButton.addClickListener(event -> register());
//    }
//
//    private void configBinder() {
//        binder.forField(confirmPassword)
//                .withValidator(confPass -> password.getValue().equals(confPass), "passwords don't match")
//                .bind(User::getConfirmPassword, User::setConfirmPassword);
//
//        binder.bindInstanceFields(this);
//    }
//
//    private void register() {
//        if (binder.writeBeanIfValid(formEntity)) {
//            String emailVal = email.getValue();
//            if (authService.userDoesNotExist(emailVal)) {
//                authService.register(emailVal, password.getValue());
//                Notification.show("Registration succeeded");
//                getUI().ifPresent(ui -> ui.navigate("login"));
//            } else {
//                email.setErrorMessage("почта уже зарегистрирована, выберите другую");
//                email.setInvalid(true);
//            }
//        }
//    }
}