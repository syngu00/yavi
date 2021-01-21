/*
 * Copyright (C) 2018-2021 Toshiaki Maki <makingx@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package am.ik.yavi.core;

import am.ik.yavi.message.SimpleMessageFormatter;
import org.junit.Test;

import java.util.Locale;
import java.util.Map;

import static am.ik.yavi.utils.MapUtils.singleArgs;
import static org.assertj.core.api.Assertions.assertThat;

public class ConstraintViolationsTest {

    @Test
    public void apply() {
        SimpleMessageFormatter messageFormatter = new SimpleMessageFormatter();
        ConstraintViolations violations = new ConstraintViolations();
        violations.add(new ConstraintViolation("foo0", "abc0", "hello0",
                singleArgs("0", 1), messageFormatter, Locale.getDefault()));
        violations.add(new ConstraintViolation("foo1", "abc1", "hello1",
                singleArgs("0", 1), messageFormatter, Locale.getDefault()));

        BindingResult bindingResult = new BindingResult();
        violations.apply(bindingResult::rejectValue);
        assertThat(bindingResult.toString())
                .isEqualTo("[foo0_abc0_[1]_hello0][foo1_abc1_[1]_hello1]");
    }

    static class BindingResult {
        final StringBuilder builder = new StringBuilder();

        public void rejectValue(String field, String errorCode, Map<String, Object> errorArgs,
                                String defaultMessage) {
            this.builder.append("[").append(field).append("_").append(errorCode)
                    .append("_").append(errorArgs).append("_")
                    .append(defaultMessage).append("]");
        }

        @Override
        public String toString() {
            return this.builder.toString();
        }
    }
}
