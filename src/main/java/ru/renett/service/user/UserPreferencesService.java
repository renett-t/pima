package ru.renett.service.user;

import ru.renett.exceptions.InvalidRequestDataException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserPreferencesService {
    void saveLastViewedArticleIdCookie(String param, HttpServletResponse response);
    Cookie getCookieOfLastViewedArticle(HttpServletRequest request) throws InvalidRequestDataException;

    void saveLastViewedArticleIdCookie(Long id, HttpServletResponse response);
}
