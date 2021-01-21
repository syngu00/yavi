package am.ik.yavi.utils;

import java.util.HashMap;
import java.util.Map;

public final class MapUtils {

    private MapUtils() {
    }

    public static Map<String, Object> singleArgs(String key, Object value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
