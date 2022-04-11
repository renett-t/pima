package ru.renett.service.old.articleService;

public interface HtmlTagsValidator {
    /**
     * removes suspicious tags
     * @return valid input
     */
    String checkStringInputTags(String body);
}
