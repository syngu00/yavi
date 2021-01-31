package am.ik.yavi.message;

import am.ik.yavi.message.subjects.BooleanValue;
import am.ik.yavi.message.subjects.IntValue;
import am.ik.yavi.message.subjects.StringValue;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

import static am.ik.yavi.core.ViolatedArguments.create;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultMessageFormatterTest {

    private final DefaultMessageFormatter messageFormatter = new DefaultMessageFormatter();

    @Test
    public void formatMessageWithRepeatingArgs() {
        String message = "${string.value} & ${string.value} & ${string.value}";

        String result = messageFormatter.format("test.message.key", message, create("string", new StringValue("value")), Locale.ENGLISH);
        assertThat(result, notNullValue());
        assertThat(result, is("value & value & value"));
    }

    @Test
    public void formatMessageWithComplexArguments() {
        Object arr = new Object[]{BigDecimal.valueOf(12L), "the meaning of life", new BooleanValue(true)};

        String message = "So the ${arr[1]} is ${int.value} and ${string.value} is ${arr[0]} and all of the statements are ${arr[2].value}";

        Map<String, Object> args = create()
                .args("string", new StringValue("my value"))
                .args("int", new IntValue(42))
                .args("arr", arr);


        String result = messageFormatter.format("test.message.key", message, args, Locale.ENGLISH);
        assertThat(result, notNullValue());
        assertThat(result, is("So the the meaning of life is 42 and my value is 12 and all of the statements are true"));
    }

}
