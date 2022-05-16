package ru.renett.service.impl;

import org.springframework.stereotype.Service;
import ru.renett.configuration.Constants;
import ru.renett.exceptions.InvalidRequestDataException;
import ru.renett.service.user.UserPreferencesService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserPreferencesManager implements UserPreferencesService {
    @Override
    public void saveLastViewedArticleIdCookie(String param, HttpServletResponse response) {
        Cookie cookie = new Cookie(Constants.COOKIE_LAST_VIEWED_ARTICLE, param);
        cookie.setMaxAge(Constants.COOKIE_LWAI_MAX_AGE);
        response.addCookie(cookie);
    }

    @Override
    public Cookie getCookieOfLastViewedArticle(HttpServletRequest request) throws InvalidRequestDataException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.COOKIE_LAST_VIEWED_ARTICLE)) {
                    return cookie;
                }
            }
            throw new InvalidRequestDataException("No relevant cookie found");
        } else {
            throw new InvalidRequestDataException("No cookies found.");
        }
    }

    @Override
    public void saveLastViewedArticleIdCookie(Long id, HttpServletResponse response) {
        this.saveLastViewedArticleIdCookie(String.valueOf(id), response);
    }
}
