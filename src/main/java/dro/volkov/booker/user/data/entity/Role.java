package dro.volkov.booker.user.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("user"), ADMIN("admin"), NOT_ACTIVE("notActive");

    private final String roleName;
}
