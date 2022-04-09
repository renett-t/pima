package ru.kpfu.itis.renett.servlets.profile;

import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.security.SecurityService;
import ru.kpfu.itis.renett.service.userService.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteProfile")
public class ProfileDeleteServlet extends HttpServlet {
    private SecurityService securityService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        securityService = (SecurityService) config.getServletContext().getAttribute(Constants.CNTX_SECURITY_SERVICE);
        userService = (UserService) config.getServletContext().getAttribute(Constants.CNTX_USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userToDelete = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        securityService.logout(request, response);
        userService.deleteUser(userToDelete);

        response.sendRedirect(request.getServletContext().getContextPath() + "/signin");
    }
}
