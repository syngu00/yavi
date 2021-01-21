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
package am.ik.yavi.message;

import am.ik.yavi.message.MessageSourceMessageFormatter.MessageSource;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class MessageSourceMessageFormatterTest {

	/*
    @Test
    void format() {
        final MessageSource messageSource = (code, args, defaultMessage, locale) -> "Message " + code + " " + Arrays.toString(args);
        final MessageSourceMessageFormatter messageFormatter = new MessageSourceMessageFormatter(messageSource);
        final String message = messageFormatter.format("demo", "", new Object[]{1, 2}, Locale.ENGLISH);
        assertThat(message).isEqualTo("Message demo [1, 2]");
    }
	*/
}