package am.ik.yavi.core;

import java.util.LinkedHashMap;

import static java.lang.System.arraycopy;

public class ViolatedArguments extends LinkedHashMap<String, Object> {

    public static ViolatedArguments create() {
        return new ViolatedArguments();
    }

    public static ViolatedArguments create(String key, Object value) {
        return new ViolatedArguments().args(key, value);
    }

    public static ViolatedArguments create(Object argument, Object... arguments) {
        return new ViolatedArguments().args(argument, arguments);
    }

    public static ViolatedArguments create(Object[] arguments) {
        return new ViolatedArguments().args(arguments);
    }

    public ViolatedArguments args(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public ViolatedArguments args(Object argument, Object... arguments) {
        Object[] merged = new Object[arguments.length + 1];
        merged[0] = argument;
        arraycopy(arguments, 0, merged, 1, arguments.length);
        return args(merged);
    }

    public ViolatedArguments args(Object[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            this.put(String.valueOf(i), arguments[0]);
        }
        return this;
    }


}
