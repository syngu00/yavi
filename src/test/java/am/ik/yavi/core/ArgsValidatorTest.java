package am.ik.yavi.core;

import am.ik.yavi.User;
import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.constraint.CharSequenceConstraint;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsValidatorTest {

    @Test
    public void argsByKeyValueToConstraint() {
        User user = new User("Demo", "demo", 100);

        Validator<User> validator = ValidatorBuilder.<User>of()
                .constraint(User::getAge, "age", c -> c.notNull().lessThan(10).args("age_key", "age_value"))
                ._string(User::getEmail, "email", c -> c.email().args("email_key", "email_value"))
                .build();

        ConstraintViolations violations = validator.validate(user);
        assertThat(violations.isValid()).isFalse();
        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations.get(0).args())
                .containsKey("age_key")
                .containsValue("age_value");
        assertThat(violations.get(1).args())
                .containsKey("email_key")
                .containsValue("email_value");
    }

    @Test
    public void argsByMultipleKeyValueToConstraint() {
        User user = new User("Demo", "demo", 100);

        Validator<User> validator = ValidatorBuilder.<User>of()
                .constraint(User::getAge, "age", c -> c.notNull().lessThan(10)
                        .args("age_key", "age_value")
                        .args("age_key_2", "age_value_2"))
                ._string(User::getEmail, "email", c -> c.email()
                        .args("email_key", "email_value")
                        .args("email_key_2", "email_value_2"))
                .build();

        ConstraintViolations violations = validator.validate(user);
        assertThat(violations.isValid()).isFalse();
        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations.get(0).args())
                .containsKeys("age_key", "age_key_2")
                .containsValues("age_value", "age_value_2");
        assertThat(violations.get(1).args())
                .containsKeys("email_key", "email_key_2")
                .containsValues("email_value", "email_value_2");
    }


    @Test
    public void argsByMapToConstraint() {
        User user = new User("Demo", "demo", 100);
        Map<String, Object> age_args = new HashMap<>();
        age_args.put("age_key", "age_value");
        Map<String, Object> email_args = new HashMap<>();
        email_args.put("email_key", "email_value");

        Validator<User> validator = ValidatorBuilder.<User>of()
                .constraint(User::getAge, "age", c -> c.notNull().lessThan(10).args(age_args))
                ._string(User::getEmail, "email", c -> c.email().args(email_args))
                .build();

        ConstraintViolations violations = validator.validate(user);
        assertThat(violations.isValid()).isFalse();
        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations.get(0).args())
                .containsKey("age_key")
                .containsValue("age_value");
        assertThat(violations.get(1).args())
                .containsKey("email_key")
                .containsValue("email_value");
    }

    @Test
    public void argsBySupplierToConstraint() {
        User user = new User("Demo", "demo", 100);
        Map<String, Object> age_args = new HashMap<>();
        age_args.put("age_key", "age_value");
        Map<String, Object> email_args = new HashMap<>();
        email_args.put("email_key", "email_value");

        Validator<User> validator = ValidatorBuilder.<User>of()
                .constraint(User::getAge, "age", c -> c.notNull().lessThan(10).args(() -> age_args))
                ._string(User::getEmail, "email", c -> c.email().args(() -> email_args))
                .build();

        ConstraintViolations violations = validator.validate(user);
        assertThat(violations.isValid()).isFalse();
        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations.get(0).args())
                .containsKey("age_key")
                .containsValue("age_value");
        assertThat(violations.get(1).args())
                .containsKey("email_key")
                .containsValue("email_value");
    }

    @Test
    public void argsByKeyValueToConstraint3() {
        User user = new User("Demo", "demo", null);

        Validator<User> validator = ValidatorBuilder.<User>of()
                .constraint(User::getAge, "age",
                        c -> c.notNull().args("key1", "value1")
                                .lessThan(10).args("age_key", "age_value"))
                ._string(User::getEmail, "email", c -> c.email().args("email_key", "email_value"))
                .build();

        ConstraintViolations violations = validator.validate(user);
        assertThat(violations.isValid()).isFalse();
        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations.get(0).args())
                .containsKey("key1")
                .containsValue("value1");
        assertThat(violations.get(1).args())
                .containsKey("email_key")
                .containsValue("email_value");
    }

}
