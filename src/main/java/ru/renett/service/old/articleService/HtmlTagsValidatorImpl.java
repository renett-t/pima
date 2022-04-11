package ru.renett.service.old.articleService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlTagsValidatorImpl implements HtmlTagsValidator {
    private final String REMOVE_SCRIPTS_PATTERN = "(<script(?:.*?)<\\/script>)";

    @Override
    public String checkStringInputTags(String body) {
        Pattern pattern = Pattern.compile(REMOVE_SCRIPTS_PATTERN, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(body);
        return matcher.replaceAll("");
    }
}
