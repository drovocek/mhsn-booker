package dro.volkov.booker.user.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dro.volkov.booker.expense.data.entity.AbstractEntity;
import dro.volkov.booker.general.data.entity.HasFilterField;
import dro.volkov.booker.general.data.entity.HasNewCheck;
import dro.volkov.booker.user.data.dict.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user_sec_data")
public class User extends AbstractEntity implements HasFilterField, HasNewCheck, Serializable {

    @NotNull
    @Email
    @Column(name = "EMAIL", unique = true)
    private String email;

    @NotNull
    @NotEmpty
    @Column(name = "USERNAME", unique = true)
    private String username;

    @NotNull
    @NotEmpty
    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Enumerated
    @Column(name = "ROLE")
    private Role role;

    @NotNull
    @Column(name = "REGISTRATION_DATE")
    private LocalDateTime registration;

    @Column(name = "LAST_ACCESS_DATE")
    private LocalDateTime lastAccess;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    @Transient
    private String filterField;

    public User(String email, String password, Role role) {
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @PrePersist
    private void beforeSave() {
        if (registration == null) {
            registration = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        }
        if (password == null) {
            this.password = RandomStringUtils.random(32);
        }
    }

    @Override
    public boolean isNew() {
        return getId() == null;
    }
}
