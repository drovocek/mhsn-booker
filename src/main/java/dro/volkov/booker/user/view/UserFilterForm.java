package dro.volkov.booker.user.view;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.general.view.FilterForm;
import dro.volkov.booker.user.data.entity.User;

@UIScope
@SpringComponent
public class UserFilterForm extends FilterForm<User> {

    public UserFilterForm() {
        super(User.class);
    }
}