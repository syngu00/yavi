package am.ik.yavi.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public final class MapUtils {

    private MapUtils() {
    }

    public static Map<String, Object> singleArgs(String key, Object value) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }
}
