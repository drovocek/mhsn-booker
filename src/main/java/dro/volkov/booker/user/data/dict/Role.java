package dro.volkov.booker.user.data.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Role {
    USER("green"), ADMIN("blue");

    @Getter
    private String colorHash;

    public String role() {
        return "ROLE_".concat(name());
    }

    @Override
    public String toString() {
        return role();
    }
}
