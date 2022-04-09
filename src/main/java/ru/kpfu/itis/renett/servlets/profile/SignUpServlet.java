package ru.kpfu.itis.renett.servlets.profile;

import ru.kpfu.itis.renett.exceptions.InvalidUserDataException;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.security.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        securityService = (SecurityService) servletContext.getAttribute(Constants.CNTX_SECURITY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String email = request.getParameter("email");
        String login = request.getParameter("login");

        try {
            User newUser = new User(firstName, secondName, email, login);
            securityService.signUp(newUser, request, response);
            removeInputValues(request.getSession());

            response.sendRedirect(getServletContext().getContextPath() + "/profile");
            return;
        } catch (InvalidUserDataException ex) {
            request.setAttribute("message", "Вы не были зарегистрированы. " + ex.getMessage());
            rememberInputValues(request.getSession(), firstName, secondName, email, login);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
    }

    private void rememberInputValues(HttpSession sess, String firstName, String secondName, String email, String login) {
        if ((firstName != null) && (firstName.length() > 0))
            sess.setAttribute("firstName", firstName);
        if ((secondName != null) && (secondName.length() > 0))
            sess.setAttribute("secondName", secondName);
        if ((email != null) && (email.length() > 0))
            sess.setAttribute("email", email);
        if ((login != null) && (login.length() > 0))
            sess.setAttribute("login", login);
    }
    private void removeInputValues(HttpSession session) {
        session.removeAttribute("firstName");
        session.removeAttribute("secondName");
        session.removeAttribute("email");
        session.removeAttribute("login");
    }
}
