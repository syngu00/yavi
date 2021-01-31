package am.ik.yavi.message;

import am.ik.yavi.message.subjects.BigDecimalValue;
import am.ik.yavi.message.subjects.BooleanValue;
import am.ik.yavi.message.subjects.IntValue;
import am.ik.yavi.message.subjects.NestedValue;
import am.ik.yavi.message.subjects.PrimitiveBooleanValue;
import am.ik.yavi.message.subjects.PrimitiveIntValue;
import am.ik.yavi.message.subjects.StringValue;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class MessagePropertiesUtilTest {

    @Test
    public void toPropertiesWithNull() {
        assertThat(MessagePropertiesUtil.toProperties(null), nullValue());
    }

    @Test
    public void toPropertiesPrimitiveIntValue() {
        Map<String, Object> props = MessagePropertiesUtil.toProperties(new PrimitiveIntValue(12));
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value"), is(true));
        assertThat(props.get("value"), is(12));
    }

    @Test
    public void toPropertiesIntValue() {
        Map<String, Object> props = MessagePropertiesUtil.toProperties(new IntValue(12));
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value"), is(true));
        assertThat(props.get("value"), is(12));
    }

    @Test
    public void toPropertiesIntValueNull() {
        Map<String, Object> props = MessagePropertiesUtil.toProperties(new IntValue());
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value"), is(true));
        assertThat(props.get("value"), nullValue());
    }

    @Test
    public void toPropertiesPrimitiveBooleanValue() {
        Map<String, Object> props = MessagePropertiesUtil.toProperties(new PrimitiveBooleanValue(true));
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value"), is(true));
        assertThat(props.get("value"), is(true));
    }

    @Test
    public void toPropertiesBooleanValue() {
        Map<String, Object> props = MessagePropertiesUtil.toProperties(new BooleanValue(true));
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value"), is(true));
        assertThat(props.get("value"), is(true));
    }

    @Test
    public void toPropertiesStringValue() {
        Map<String, Object> props = MessagePropertiesUtil.toProperties(new StringValue("string"));
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value"), is(true));
        assertThat(props.get("value"), is("string"));
    }

    @Test
    public void toPropertiesBigDecimalValue() {
        Map<String, Object> props = MessagePropertiesUtil.toProperties(new BigDecimalValue(BigDecimal.valueOf(1L)));
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value"), is(true));
        assertThat(props.get("value"), is(BigDecimal.valueOf(1L)));
    }


    @Test
    public void toPropertiesEmptyArray() {
        Map<String, Object> props = MessagePropertiesUtil.toProperties(new Object[]{});
        assertThat(props, notNullValue());
        assertThat(props.size(), is(0));
    }


    @Test
    public void toPropertiesSimpleArray() {
        Map<String, Object> props = MessagePropertiesUtil.toProperties(new Object[]{"string", 12});
        assertThat(props, notNullValue());
        assertThat(props.containsKey("[0]"), is(true));
        assertThat(props.get("[0]"), is("string"));
        assertThat(props.containsKey("[1]"), is(true));
        assertThat(props.get("[1]"), is(12));
    }

    @Test
    public void toPropertiesObjectArray() {
        Map<String, Object> props = MessagePropertiesUtil.toProperties(new Object[]{new StringValue("string"), new IntValue(12)});
        assertThat(props, notNullValue());
        assertThat(props.containsKey("[0].value"), is(true));
        assertThat(props.get("[0].value"), is("string"));
        assertThat(props.containsKey("[1].value"), is(true));
        assertThat(props.get("[1].value"), is(12));
    }

    @Test
    public void toPropertiesList() {
        List<Object> values = new ArrayList<>();
        values.add("string");
        values.add(12);

        Map<String, Object> props = MessagePropertiesUtil.toProperties(values);
        assertThat(props, notNullValue());
        assertThat(props.containsKey("[0]"), is(true));
        assertThat(props.get("[0]"), is("string"));
        assertThat(props.containsKey("[1]"), is(true));
        assertThat(props.get("[1]"), is(12));
    }

    @Test
    public void toPropertiesObjectList() {
        List<Object> values = new ArrayList<>();
        values.add(new StringValue("string"));
        values.add(new IntValue(12));

        Map<String, Object> props = MessagePropertiesUtil.toProperties(values);
        assertThat(props, notNullValue());
        assertThat(props.containsKey("[0].value"), is(true));
        assertThat(props.get("[0].value"), is("string"));
        assertThat(props.containsKey("[1].value"), is(true));
        assertThat(props.get("[1].value"), is(12));
    }

    @Test
    public void toPropertiesMap() {
        Map<String, Object> values = new HashMap();
        values.put("value1", "string");
        values.put("value2", 12);

        Map<String, Object> props = MessagePropertiesUtil.toProperties(values);
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value1"), is(true));
        assertThat(props.get("value1"), is("string"));
        assertThat(props.containsKey("value2"), is(true));
        assertThat(props.get("value2"), is(12));
    }

    @Test
    public void toPropertiesObjectMap() {
        Map<String, Object> values = new HashMap();
        values.put("value1", new StringValue("string"));
        values.put("value2", new IntValue(12));

        Map<String, Object> props = MessagePropertiesUtil.toProperties(values);
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value1.value"), is(true));
        assertThat(props.get("value1.value"), is("string"));
        assertThat(props.containsKey("value2.value"), is(true));
        assertThat(props.get("value2.value"), is(12));
    }


    @Test
    public void toPropertiesNestedObject() {
        NestedValue<StringValue> values = new NestedValue<>(new StringValue("string"));

        Map<String, Object> props = MessagePropertiesUtil.toProperties(values);
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value.value"), is(true));
        assertThat(props.get("value.value"), is("string"));
    }

    @Test
    public void toPropertiesMultipleNestedObject() {
        NestedValue<NestedValue<StringValue>> values = new NestedValue<>(new NestedValue<>(new StringValue("string")));

        Map<String, Object> props = MessagePropertiesUtil.toProperties(values);
        assertThat(props, notNullValue());
        assertThat(props.containsKey("value.value.value"), is(true));
        assertThat(props.get("value.value.value"), is("string"));
    }

}
