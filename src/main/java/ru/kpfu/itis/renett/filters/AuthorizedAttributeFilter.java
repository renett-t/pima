package ru.kpfu.itis.renett.filters;

import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.security.SecurityService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthorizedAttributeFilter implements Filter {
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        securityService = (SecurityService) filterConfig.getServletContext().getAttribute(Constants.CNTX_SECURITY_SERVICE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (securityService.isAuthenticated(request)) {
            request.setAttribute(Constants.REQUEST_ATTRIBUTE_AUTHORIZED, true);
        } else {
            request.setAttribute(Constants.REQUEST_ATTRIBUTE_AUTHORIZED, false);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
