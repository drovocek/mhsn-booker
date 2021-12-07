package dro.volkov.booker.user.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dro.volkov.booker.expense.data.entity.HasFilterField;
import dro.volkov.booker.expense.data.entity.HasNewCheck;
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

import static dro.volkov.booker.util.StrUtil.asUsername;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user_sec_data")
public class User implements HasFilterField, HasNewCheck, Serializable {

    @NotNull
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    @JsonIgnore
    private String password;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    @NotNull
    @Enumerated
    private Role role;

    @NotNull
    private LocalDateTime registrationDate;

    private LocalDateTime lastAccessDate;

    private boolean active;

    private boolean enabled;

    @Transient
    private String filterField;

    public User(String email, String password, Role role) {
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @PrePersist
    private void setDateIfNotSet() {
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        }
        if (password == null) {
            this.password = RandomStringUtils.random(32);
        }
        if (email != null) {
            this.username = asUsername(email);
        }
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
