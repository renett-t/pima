package ru.kpfu.itis.renett.service.articleService;

public interface HtmlTagsValidator {
    /**
     * removes suspicious tags
     * @return valid input
     */
    String checkStringInputTags(String body);
}
