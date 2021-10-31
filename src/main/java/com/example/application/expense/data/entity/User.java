package com.example.application.expense.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Setter
@Getter
@ToString
@Entity
public class User extends AbstractEntity {

    @Length(min = 3, max = 15)
    private String firstName;

    @Length(min = 3, max = 15)
    private String lastName;

    @Length(min = 3, max = 15)
    private String nikName;

    @NotNull
    private LocalDateTime registrationDate;

    @Email
    private String email;

    @Length(min = 7, max = 20)
    private String password;

    @PrePersist
    private void setDateIfNotSet() {
        registrationDate = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
    }
}
