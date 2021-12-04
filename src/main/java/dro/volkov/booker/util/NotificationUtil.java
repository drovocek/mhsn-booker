package dro.volkov.booker.util;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NotificationUtil {

    public static void noticeSSS(String message) {
        Notification
                .show(message, 5000, Notification.Position.BOTTOM_START)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    public static void noticeERR(String message) {
        Notification
                .show(message, 5000, Notification.Position.BOTTOM_START)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    public static void notice(String message, NotificationVariant theme) {
        Notification
                .show(message, 5000, Notification.Position.BOTTOM_START)
                .addThemeVariants(theme);
    }
}
