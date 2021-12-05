package dro.volkov.booker.user.data.entity;

public enum Role {
    USER, ADMIN;

    public String role() {
        return "ROLE_".concat(name());
    }


    @Override
    public String toString() {
        return role();
    }
}
