package ru.renett.utils.impl;

import org.springframework.stereotype.Component;
import ru.renett.utils.HtmlTagsValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HtmlTagsValidatorImpl implements HtmlTagsValidator {
    private final String REMOVE_SCRIPTS_PATTERN = "(<script(?:.*?)<\\/script>)";

    @Override
    public String checkStringInputTags(String body) {
        Pattern pattern = Pattern.compile(REMOVE_SCRIPTS_PATTERN, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(body);
        return matcher.replaceAll("");
    }
}
