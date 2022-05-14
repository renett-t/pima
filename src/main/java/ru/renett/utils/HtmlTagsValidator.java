package ru.renett.utils;

public interface HtmlTagsValidator {
    /**
     * removes suspicious tags
     * @return valid input
     */
    String checkStringInputTags(String body);
}
