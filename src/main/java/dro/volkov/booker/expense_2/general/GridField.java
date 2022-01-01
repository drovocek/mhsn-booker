package dro.volkov.booker.expense_2.general;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GridField {

    String title();
}
