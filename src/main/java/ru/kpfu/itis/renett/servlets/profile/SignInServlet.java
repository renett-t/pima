package ru.kpfu.itis.renett.servlets.profile;

import ru.kpfu.itis.renett.exceptions.InvalidUserDataException;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.security.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        securityService = (SecurityService) servletContext.getAttribute(Constants.CNTX_SECURITY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/signin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            securityService.signIn(login, password, request, response);

            response.sendRedirect(getServletContext().getContextPath()  + "/profile");
            return;
        } catch (InvalidUserDataException e) {
            request.setAttribute("message", e.getMessage());
        }

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signin.jsp").forward(request, response);
    }
}
