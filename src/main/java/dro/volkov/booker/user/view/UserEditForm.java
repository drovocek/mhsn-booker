package dro.volkov.booker.user.view;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.user.data.dict.Role;
import dro.volkov.booker.user.data.entity.User;

@UIScope
@SpringComponent
public class UserEditForm extends EditForm<User> {

    private TextField email;
    private ComboBox<Role> role;
    private Checkbox enabled;

    public UserEditForm() {
        super(User.class);
    }

    @Override
    protected void configFields() {
        this.email = new TextField("Email");
        this.role = createRoleComboBox();
        this.enabled = new Checkbox("Enabled");
        addFields(email, role, enabled);
    }

    protected ComboBox<Role> createRoleComboBox() {
        return new ComboBox<>() {
            {
                setLabel("Role");
                setItems(Role.values());
                setItemLabelGenerator(Role::name);
            }
        };
    }
}
