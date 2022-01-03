package dro.volkov.booker.expense_2.util;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import dro.volkov.booker.expense_2.DataService;
import dro.volkov.booker.expense_2.general.HasName;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppFields implements ApplicationContextAware {

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        AppFields.appContext = appContext;
    }

    public static Object getBean(String dataServiceName) {
        return appContext.getBean(dataServiceName);
    }

    public static TextField createTextField(String label, String fieldName) {
        return new TextField() {{
            setLabel(label);
            getElement().setProperty("fieldName", fieldName);
            setClearButtonVisible(true);
            setWidth("100%");
        }};
    }

    public static TextArea createTextArea(String label, String fieldName) {
        return new TextArea() {{
            setLabel(label);
            getElement().setProperty("fieldName", fieldName);
            setClearButtonVisible(true);
            setWidth("100%");
        }};
    }

    public static DatePicker createDatePicker(String label, String fieldName) {
        return new DatePicker() {{
            setLabel(label);
            getElement().setProperty("fieldName", fieldName);
            setClearButtonVisible(true);
            setWidth("100%");
        }};
    }

    public static NumberField createNumberField(String label, String fieldName) {
        return new NumberField() {{
            setLabel(label);
            getElement().setProperty("fieldName", fieldName);
            setClearButtonVisible(true);
            setWidth("100%");
        }};
    }

    public static <T extends HasName> ComboBox<T> createCheckBox(String label, String fieldName, String dataServiceQualifier) {
        return new ComboBox<>() {

            private final List<T> dataStore = new ArrayList<>();

            @Override
            protected void onAttach(AttachEvent attachEvent) {
                super.onAttach(attachEvent);
                dataStore.addAll(((DataService<T>) getBean(dataServiceQualifier)).getAll());
            }

            {
                setItems(dataStore);
                setLabel(label);
                getElement().setProperty("fieldName", fieldName);
                setItemLabelGenerator(T::getName);
                setWidth("100%");
            }
        };
    }
}
