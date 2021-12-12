package dro.volkov.booker.user.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dro.volkov.booker.expense.data.entity.AbstractEntity;
import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.data.entity.HasFilterField;
import dro.volkov.booker.general.data.entity.HasNewCheck;
import dro.volkov.booker.user.data.dict.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "APP_USER", uniqueConstraints = {
        @UniqueConstraint(columnNames = "EMAIL", name = "APP_USERS_UNIQUE_EMAIL_IDX"),
        @UniqueConstraint(columnNames = "USERNAME", name = "APP_USERS_UNIQUE_USERNAME_IDX")
})
public class User extends AbstractEntity implements HasFilterField, HasNewCheck, Serializable {

    @Email
    @NotBlank
    @Length(max = 100)
    @Column(name = "EMAIL", nullable = false, unique = true, length = 100, updatable = false)
    private String email;

    @NotBlank
    @Length(max = 50)
    @Column(name = "USERNAME", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank
    @Length(max = 100)
    @JsonIgnore
    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @NotNull
    @Enumerated
    @Column(name = "ROLE", nullable = false)
    private Role role;

    @NotNull
    @Column(name = "REGISTRATION_DATE", nullable = false)
    private LocalDateTime registration;

    @Column(name = "LAST_ACCESS_DATE")
    private LocalDateTime lastAccess;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("date DESC")
    @ToString.Exclude
    private List<Expense> expenses;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    @Transient
    private String filterField;

    public User(Integer id) {
        super(id);
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
