package ru.kpfu.itis.renett.servlets.profile;

import ru.kpfu.itis.renett.exceptions.InvalidUserDataException;
import ru.kpfu.itis.renett.exceptions.VkAuthorizationException;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.security.SecurityService;
import ru.kpfu.itis.renett.service.security.VkService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vkOauth")
public class VkServlet extends HttpServlet {
    private SecurityService securityService;
    private VkService vkService;
    private String urlToAuthorize;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        securityService = (SecurityService) config.getServletContext().getAttribute(Constants.CNTX_SECURITY_SERVICE);
        vkService = (VkService) config.getServletContext().getAttribute(Constants.CNTX_VK_SERVICE);
        urlToAuthorize = String.format("https://oauth.vk.com/authorize?client_id=%d&display=page&redirect_uri=%s&scope=email&response_type=code&v=5.131",
                VkService.CLIENT_ID,
                "http://localhost:8088" + config.getServletContext().getContextPath() + "/vkOauth");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("code") != null) {
            try {
                User user = vkService.getUserFromVk(request.getParameter("code"));
                securityService.signIn(user.getLogin(), null, request, response);

                response.sendRedirect(getServletContext().getContextPath()  + "/profile");
                return;
            } catch (InvalidUserDataException | VkAuthorizationException e) {
                request.setAttribute("message", e.getMessage());
            }
        } else {
            response.sendRedirect(urlToAuthorize);
            return;
        }

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signin.jsp").forward(request, response);
    }
}
