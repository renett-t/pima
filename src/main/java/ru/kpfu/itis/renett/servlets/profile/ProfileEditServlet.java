package ru.kpfu.itis.renett.servlets.profile;

import ru.kpfu.itis.renett.exceptions.InvalidUserDataException;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.security.SecurityService;
import ru.kpfu.itis.renett.service.userService.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editProfile")
public class ProfileEditServlet extends HttpServlet {
    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        securityService = (SecurityService) servletContext.getAttribute(Constants.CNTX_SECURITY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("user", request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME));
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/profile-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String email = request.getParameter("email");
        String login = request.getParameter("login");

        try {
            User editedUser = new User(firstName, secondName, email, login);
            securityService.editUserData(editedUser, request, response);

            response.sendRedirect(getServletContext().getContextPath() + "/profile");
            return;
        } catch (InvalidUserDataException ex) {
            request.setAttribute("message", "Данные не были сохранены. " + ex.getMessage());
            request.setAttribute("user", request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME));
        }

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/profile-edit.jsp").forward(request, response);
    }
}
