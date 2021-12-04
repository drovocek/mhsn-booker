package dro.volkov.booker.util;

public class StrUtil {

    public static String asUsername(String email){
        return email.split("@")[0];
    }
}
