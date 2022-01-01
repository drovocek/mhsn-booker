package dro.volkov.booker.expense_2.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.data.binder.Binder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class FormConfigurator {

    public static <T> void bindFields(Binder<T> binder, HasValue<?, ?>... fields) {
        Arrays.stream(fields).forEach(field -> {
            if (field instanceof Component) {
                String fieldName = ((Component) field).getElement().getProperty("fieldName");
                if (fieldName == null) {
                    log.warn(String.format("Field %s has no fieldName property", field.getClass().getSimpleName()));
                } else {
                    binder.forField((HasValue<?, ?>) field).bind(fieldName);
                }
            } else {
                throw new RuntimeException("You can bind only instanceof Component fields");
            }
        });
    }
}
