package ru.kpfu.itis.renett.service.userService;

import ru.kpfu.itis.renett.exceptions.InvalidRequestDataException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserPreferencesInterface {
    void saveLastViewedArticleIdCookie(int id, HttpServletResponse response);
    Cookie getCookieOfLastViewedArticle(HttpServletRequest request) throws InvalidRequestDataException;
}
