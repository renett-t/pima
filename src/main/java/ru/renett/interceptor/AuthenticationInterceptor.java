package ru.renett.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ru.renett.configuration.Constants.IS_AUTHENTICATED_ATTR;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")  ;
            System.out.println("____________________");
            System.out.println("AUTH CHECK --- " + isAuthenticated);
            modelAndView.getModelMap().put(IS_AUTHENTICATED_ATTR, isAuthenticated);
        } else {
            System.out.println("____________________");
            System.out.println("NO MODEL AND VIEW - AUTH CHECK FALSE");
        }
    }
}
