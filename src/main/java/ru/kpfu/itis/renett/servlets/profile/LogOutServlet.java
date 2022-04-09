package ru.kpfu.itis.renett.servlets.profile;

import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.security.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        securityService = (SecurityService) config.getServletContext().getAttribute(Constants.CNTX_SECURITY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        securityService.logout(request, response);
        response.sendRedirect(getServletContext().getContextPath() + "/signin");
    }
}
