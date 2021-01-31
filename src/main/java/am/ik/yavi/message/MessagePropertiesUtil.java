package am.ik.yavi.message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class MessagePropertiesUtil {
    private MessagePropertiesUtil() {
    }

    public static <T> Map<String, Object> toProperties(T t) {
        if (t == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        toProperties(result, "", t);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> void toProperties(Map<String, Object> result, String prefix, T t) {
        if (t instanceof Map) {
            mapToProperties(result, prefix, (Map<Object, Object>) t);
        } else if (isIterable(t)) {
            iterableToProperties(result, prefix, getIterator(t));
        } else {
            objectToProperties(result, prefix, t);
        }

    }

    public static void mapToProperties(Map<String, Object> result, String prefix, Map<Object, Object> map) {
        map.forEach((o, o2) -> {
            String name = String.format("%s.%s", prefix, o.toString());
            if (isBasic(o2)) {
                put(result, name, o2);
            } else {
                toProperties(result, name, o2);
            }
        });
    }

    public static void iterableToProperties(Map<String, Object> result, String prefix, Iterator<?> iterator) {
        int i = 0;
        while (iterator.hasNext()) {
            Object next = iterator.next();
            String name = String.format("%s[%s]", prefix, i++);
            if (isBasic(next)) {
                put(result, name, next);
            } else {
                toProperties(result, name, next);
            }
        }
    }

    public static <T> void objectToProperties(Map<String, Object> result, String prefix, T t) {
        for (Method method : getters(t.getClass())) {
            Object value = invokeMethod(method, t);
            String name = String.format("%s.%s", prefix, toFieldName(method));
            if (isBasic(value)) {
                put(result, name, value);
            } else {
                toProperties(result, name, value);
            }
        }
    }


    private static void put(Map<String, Object> result, String key, Object value) {
        if (key.startsWith("[")) {
            result.put(key, value);
        } else {
            result.put(key.substring(1), value);
        }
    }

    private static boolean isIterable(Object obj) {
        return obj.getClass().isArray() || obj instanceof Iterable<?>;
    }

    private static Iterator<?> getIterator(Object obj) {
        if (obj instanceof Iterable<?>) {
            return ((Iterable<?>) obj).iterator();
        } else if (obj.getClass().isArray()) {
            return convert((Object[]) obj).iterator();
        }

        throw new InvalidParameterException("The given object can't be converted to Iterator");
    }

    private static boolean isBasic(Object obj) {
        if (obj == null) {
            return true;
        }
        return isPrimitive(obj) || isBoolean(obj) || isNumeric(obj) || isCharSeq(obj);
    }

    private static boolean isPrimitive(Object obj) {
        return obj.getClass().isPrimitive();
    }

    private static boolean isNumeric(Object obj) {
        return Number.class.isAssignableFrom(obj.getClass());
    }

    private static boolean isBoolean(Object obj) {
        return Boolean.class.isAssignableFrom(obj.getClass());
    }


    private static boolean isCharSeq(Object obj) {
        return CharSequence.class.isAssignableFrom(obj.getClass());
    }

    private static String toFieldName(Method method) {
        String methodName = method.getName();
        if (method.getReturnType().equals(boolean.class)) {
            methodName = methodName.replace("is", "");
        } else {
            methodName = methodName.replace("get", "");
        }
        return methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
    }


    private static List<Method> getters(Class<?> sourceClazz) {
        return Arrays.stream(sourceClazz.getMethods())
                .filter(method -> !method.getName().equals("getClass"))
                .filter(method -> method.getName().startsWith("is") || method.getName().startsWith("get"))
                .filter(method -> method.getParameterTypes().length == 0)
                .filter(method -> !void.class.equals(method.getReturnType()))
                .collect(Collectors.toList());
    }

    private static <T> Iterable<T> convert(T[] arr) {
        return () -> Arrays.stream(arr).iterator();
    }

    private static <T> Object invokeMethod(Method method, T target) {
        try {
            return method.invoke(target);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("something went very wrong", e);
        }
    }
}
