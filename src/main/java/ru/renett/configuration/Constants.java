package ru.renett.configuration;

import java.io.File;

public class Constants {
    public static final String HASHING_ALGORITHM_NAME = "SHA-256";

    public static final String DB_URL = "db.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_DRIVER = "db.driver";
    public static final String DB_POOL_SIZE = "db.max-pool-size";

    public static final String CNTX_SECURITY_SERVICE = "securityService";
    public static final String CNTX_ARTICLE_GET_SERVICE = "articleGetService";
    public static final String CNTX_ARTICLE_SAVE_SERVICE = "articleSaveService";
    public static final String CNTX_VK_SERVICE = "vkSerice";
    public static final String CNTX_USER_SERVICE = "userService";
    public static final String CNTX_PREFERENCES_MANAGER = "preferencesManager";
    public static final String CNTX_REQUEST_VALIDATOR = "requestValidator";

    public static final String COOKIE_AUTHORIZED_NAME = "uuid-user";
    public static final String COOKIE_LAST_VIEWED_ARTICLE = "lwai";
    public static final String SESSION_USER_ATTRIBUTE_NAME = "auth_user";
    public static final String REQUEST_ATTRIBUTE_AUTHORIZED = "authorized";                          // renaming here is dangerous!! then edit jsp files

    public static final String STORAGE_URL = "resources" + File.separator + "articles";
    public static final String CHAR_ENCODING = "UTF-8";
    public static final String CHAR_ENCODING_ATTR_NAME = "encoding";
    public static final String DEFAULT_THUMBNAIL = "guitar-background.jpg";

    // SECURITY CONFIG
    public static final int SECURITY_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 365;

    // ATTRIBUTES FOR VIEW
    public static final String IS_AUTHENTICATED_ATTR = "isAuthenticated";
    public static final String USER_ATTR = "user";
    public static final String SEARCH_TAG_ATTR = "searchTag";
    public static final String LIKED_ARTICLES_ATTR = "likedArticles";
    public static final String USER_ARTICLES_ATTR = "userArticles";
    public static final String ARTICLES_ATTR = "articles";
    public static final String MESSAGE_ATTR = "message";
    public static final String LIKED_ATTR = "liked";
    public static final String OWNED_ATTR = "owned";
    public static final String TAGS_ATTR = "tags";
}
