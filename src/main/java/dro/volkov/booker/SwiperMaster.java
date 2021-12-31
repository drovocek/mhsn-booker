package dro.volkov.booker;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.internal.JsonUtils;
import elemental.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public interface SwiperMaster {

    default void asSwipeEventGenerator(Component target){
        target.addAttachListener(attachEvent ->
                target.getElement()
                        .executeJs("window.swiper_behavior.swipe($0, $1)", target, swipeSettings()));
    }

    default JsonObject swipeSettings() {
        Map<String, String> joMap = new HashMap<>();
        joMap.put("maxTime", "1000");
        joMap.put("minTime", "100");
        joMap.put("maxDist", "150");
        joMap.put("minDist", "60");
        return JsonUtils.mapToJson(joMap);
    }
}
