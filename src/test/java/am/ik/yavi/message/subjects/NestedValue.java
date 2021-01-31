package am.ik.yavi.message.subjects;

public class NestedValue<T> {

    private T value;

    public NestedValue() {
    }

    public NestedValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
