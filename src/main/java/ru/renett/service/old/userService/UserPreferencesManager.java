package ru.renett.service.old.userService;

import ru.renett.configuration.Constants;
import ru.renett.exceptions.InvalidRequestDataException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserPreferencesManager implements UserPreferencesInterface {
    @Override
    public void saveLastViewedArticleIdCookie(Long id, HttpServletResponse response) {
        Cookie authorizedCookie = new Cookie(Constants.COOKIE_LAST_VIEWED_ARTICLE, String.valueOf(id));
        authorizedCookie.setMaxAge(60*60*24*100);
        response.addCookie(authorizedCookie);
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
            throw new InvalidRequestDataException("No relevant found");
        } else {
            throw new InvalidRequestDataException("No cookies found.");
        }
    }
}
