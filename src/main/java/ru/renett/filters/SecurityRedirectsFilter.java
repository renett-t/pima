package ru.renett.filters;

import ru.renett.configuration.Constants;
import ru.renett.service.old.SecurityService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebFilter("/*")
public class SecurityRedirectsFilter implements Filter {
    private SecurityService securityService;
    private Map<String, String> paths;
    private static final String DEFAULT_REDIRECT_PATH = "/signin";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        securityService = (SecurityService) filterConfig.getServletContext().getAttribute(Constants.CNTX_SECURITY_SERVICE);
        paths = initializePathsMap();
    }

    private Map<String, String> initializePathsMap() {
        List<String> pathsToProtect = Arrays.asList("/profile", "/deleteProfile", "/editProfile", "/logout", "/newArticle", "/deleteArticle", "/editArticle", "/like", "/newComment");
        return pathsToProtect.stream().collect(Collectors.toMap(key -> key, value -> DEFAULT_REDIRECT_PATH));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean isAccessible = true;
        String pathToRedirect = "";

        if (!securityService.isAuthenticated(request)) {
            for (Map.Entry<String, String> entry: paths.entrySet()){
                if (request.getRequestURI().startsWith(request.getServletContext().getContextPath() + entry.getKey())) {
                    pathToRedirect = entry.getValue();
                    isAccessible = false;
                }
            }
        }

        if (!isAccessible) {
            response.sendRedirect(request.getServletContext().getContextPath() + pathToRedirect);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
