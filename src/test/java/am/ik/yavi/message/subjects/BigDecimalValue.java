package am.ik.yavi.message.subjects;

import java.math.BigDecimal;

public class BigDecimalValue {
    private BigDecimal value;

    public BigDecimalValue() {
    }

    public BigDecimalValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
