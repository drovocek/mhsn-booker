package dro.volkov.booker.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user_sec_data")
public class User {

    @NotNull
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @JsonIgnore
    private String password;

    private String confirmPassword;

    @Transient
    private String username;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @NotNull
    private LocalDateTime registrationDate;

    public User(String email, String password, Role role) {
        this.password = password;
        this.email = email;
        this.roles = Collections.singleton(role);
    }

    @PrePersist
    private void setDateIfNotSet() {
        registrationDate = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
    }

    @PostPersist
    private void addUserName() {
        this.username = email.split("@")[0];
    }
}
