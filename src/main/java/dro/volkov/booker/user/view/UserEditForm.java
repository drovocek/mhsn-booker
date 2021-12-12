package dro.volkov.booker.user.view;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.general.view.EditForm;
import dro.volkov.booker.user.data.UserCrudService;
import dro.volkov.booker.user.data.dict.Role;
import dro.volkov.booker.user.data.entity.User;

@UIScope
@SpringComponent
public class UserEditForm extends EditForm<User> {

    private final UserCrudService service;
    private TextField email;
    private TextField username;
    private ComboBox<Role> role;
    private Checkbox enabled;

    public UserEditForm(UserCrudService service) {
        super(User.class);
        this.service = service;
    }

    @Override
    protected void configFields() {
        this.email = new TextField("Email");
        this.username = new TextField("Username");
        this.role = createRoleComboBox();
        this.enabled = new Checkbox("Enabled");
        addFields(email, username, role, enabled);
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

    @Override
    protected void validateAndPushSave() {
        if (binder.writeBeanIfValid(formEntity)) {
            if (formEntity.isNew()) {
                boolean usernameNotExist = service.usernameNotExist(username.getValue());
                boolean emailNotExist = service.emailNotExist(email.getValue());
                if (!usernameNotExist) {
                    System.out.println("USERNAME_ERR");
                    username.setErrorMessage("Username is already in use");
                    username.setInvalid(true);
                }
                if (!emailNotExist) {
                    System.out.println("EMAIL_ERR");
                    email.setErrorMessage("Email is already in use");
                    email.setInvalid(true);
                }
                if (usernameNotExist && emailNotExist) {
                    fireUISaveEvent(formEntity);
                    close();
                }
            } else {
                fireUISaveEvent(formEntity);
                close();
            }
        }
    }

    @Override
    protected void open(User entity) {
        super.open(entity);
        email.setEnabled(entity.isNew());
        username.setEnabled(entity.isNew());
        if (entity.isNew()) {
            role.setValue(Role.USER);
        }
    }
}
