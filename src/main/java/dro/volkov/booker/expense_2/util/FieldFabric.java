package dro.volkov.booker.expense_2.util;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import dro.volkov.booker.expense_2.general.HasName;

import java.util.List;

public class FieldFabric {

    public static TextField createTextField(String label, String fieldName) {
        return new TextField() {
            {
                setLabel(label);
                getElement().setProperty("fieldName", fieldName);
                setClearButtonVisible(true);
            }
        };
    }

    public static DatePicker createDatePicker(String label, String fieldName) {
        return new DatePicker() {
            {
                setLabel(label);
                getElement().setProperty("fieldName", fieldName);
                setClearButtonVisible(true);
            }
        };
    }

    public static NumberField createNumberField(String label, String fieldName) {
        return new NumberField() {
            {
                setLabel(label);
                getElement().setProperty("fieldName", fieldName);
                setClearButtonVisible(true);
            }
        };
    }

    public static <T extends HasName> ComboBox<T> createCheckBox(String label, String fieldName, List<T> data) {
        return new ComboBox<>() {
            {
                setItems(data);
                setLabel(label);
                getElement().setProperty("fieldName", fieldName);
                setItemLabelGenerator(T::getName);
            }
        };
    }
}
