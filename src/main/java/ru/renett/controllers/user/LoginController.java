package ru.renett.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.renett.configuration.Constants;
import ru.renett.utils.VkOauthUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/signIn")
@RequiredArgsConstructor
public class LoginController {

    private final VkOauthUtils vkOauthUtils;

    @GetMapping()
    public String getLoginPage(@RequestParam(value = "error", required = false) String error, HttpServletRequest request, ModelMap map) {
        map.put(Constants.VK_OAUTH_URL_ATTR, vkOauthUtils.getOauthRequestUrl());

        HttpSession session = request.getSession(false);
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null)
                map.put(Constants.ERROR_ATTR, ex.getMessage());
        }

        return "sign_in";
    }
}
