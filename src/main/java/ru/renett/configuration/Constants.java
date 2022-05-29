package ru.renett.configuration;

public class Constants {
    // COOKIE
    public static final String COOKIE_LAST_VIEWED_ARTICLE = "lwai";
    public static final int COOKIE_LWAI_MAX_AGE = 60 * 60 * 24 * 100;
    public static final String COOKIE_LOCALE = "locale";
    public static final int COOKIE_LOCALE_MAX_AGE = 60 * 60 * 24 * 365;
    public static final String LOCALE_CHANGE_PARAM = "lang";

    // SOURCES
    public static final String MESSAGES_SOURCE = "classpath:messages/messages";

    public static final String CHAR_ENCODING = "UTF-8";

    public static final String DEFAULT_THUMBNAIL = "guitar-background.jpg";

    public static final String REST_DEFAULT_PAGE = "0";
    public static final String REST_DEFAULT_LIMIT = "5";

    // SECURITY CONFIG
    public static final int SECURITY_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 365;

    // ATTRIBUTES FOR VIEW
    public static final String IS_AUTHENTICATED_ATTR = "isAuthenticated";
    public static final String SIGN_UP_FORM_ATTR = "signUpForm";
    public static final String USER_ATTR = "user";
    public static final String SEARCH_TAG_ATTR = "searchTag";
    public static final String LIKED_ARTICLES_ATTR = "likedArticles";
    public static final String USER_ARTICLES_ATTR = "userArticles";
    public static final String ARTICLES_ATTR = "articles";
    public static final String MESSAGE_ATTR = "message";
    public static final String LIKED_ATTR = "liked";
    public static final String OWNED_ATTR = "owned";
    public static final String TAGS_ATTR = "tags";
    public static final String ARTICLE_ATTR = "article";
    public static final String UPDATE_PROFILE_ATTR = "updateUserForm";

    public static final String COMMENT_ATTR = "comment";

    public static final String SPRING_MACRO_CONTEXT_ATTR = "springMacroRequestContext";

    public static final String LIKE_SOURCE = "/assets/icons/like-active.png";
    public static final String DISLIKE_SOURCE = "/assets/icons/like.png";
}
