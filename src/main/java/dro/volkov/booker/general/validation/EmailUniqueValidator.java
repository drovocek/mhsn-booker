package dro.volkov.booker.general.validation;

import dro.volkov.booker.user.data.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailUniqueValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserCrudService service;

    @Override
    public void initialize(UniqueEmail uniqueEmail) {
        System.out.println("INIT");
        System.out.println(service);
    }

    @PostConstruct
    private void post() {
        System.out.println("POST");
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (service != null) {
            System.out.println("VALIDATE_EMAIL");
            return service.getByEmail(email).isEmpty();
        }
        return true;
    }
}