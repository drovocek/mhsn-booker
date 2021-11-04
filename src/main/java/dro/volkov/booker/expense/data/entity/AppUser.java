package dro.volkov.booker.expense.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Setter
@Getter
@ToString
@Entity
public class AppUser extends AbstractEntity {

    @Length(min = 3, max = 15)
    private String firstName;

    @Length(min = 3, max = 15)
    private String lastName;

    @Length(min = 3, max = 15)
    private String nikName;

    @Email
    private String email;

    @Length(min = 7, max = 20)
    private String password;
}
