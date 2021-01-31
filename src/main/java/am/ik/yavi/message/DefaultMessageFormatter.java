package am.ik.yavi.message;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultMessageFormatter implements MessageFormatter {
    private static final Pattern REGEX = Pattern.compile("(\\$\\{.*?\\})");

    @Override
    public String format(String messageKey, String defaultMessageFormat, Map<String, Object> args, Locale locale) {
        return format(defaultMessageFormat, args);
    }

    private String format(String defaultMessageFormat, Map<String, Object> args) {
        Map<String, Object> flatArgs = MessagePropertiesUtil.toProperties(args);
        List<String> keys = getKeysToReplace(defaultMessageFormat);

        for (String key : keys) {
            defaultMessageFormat = defaultMessageFormat.replaceAll(Pattern.quote(key), flatArgs.get(sanitizeKey(key)).toString());
        }

        return defaultMessageFormat;
    }

    private List<String> getKeysToReplace(String defaultMessageFormat) {
        Matcher regexMatcher = REGEX.matcher(defaultMessageFormat);

        List<String> matched = new ArrayList<>();
        while (regexMatcher.find()) {
            matched.add(regexMatcher.group(1));
        }

        return matched;
    }

    private String sanitizeKey(String key) {
        return key.replace("${", "").replace("}", "");
    }


}
