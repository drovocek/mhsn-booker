package dro.volkov.booker.user.view;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.user.data.entity.Role;
import dro.volkov.booker.user.data.entity.User;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
public class UserEditForm extends EditForm<User> {

    private final TextField email = new TextField("Email");
    private final Checkbox enabled = new Checkbox("Enabled");
    private final ComboBox<Role> role = new ComboBox<>("Role");

    public UserEditForm() {
        super(User.class);
    }

    protected void configFields() {
        role.setItems(Role.values());
        role.setItemLabelGenerator(Role::getRoleName);
    }

    @PostConstruct
    public void initView() {
        super.initView();
        addFields(email, enabled, role);
    }

    @Override
    protected void asEditForm(boolean asEdit) {
        super.asEditForm(asEdit);
        enabled.setVisible(asEdit);
        email.setVisible(!asEdit);
    }
}
