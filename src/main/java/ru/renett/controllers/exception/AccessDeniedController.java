package ru.renett.controllers.exception;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.renett.configuration.Constants;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deny")
public class AccessDeniedController {
    private final static Logger logger = LoggerFactory.getLogger(AccessDeniedController.class);

    @GetMapping()
    public String getAccessDeniedPage(HttpServletResponse response, ModelMap map) {
        response.setStatus(403);

        map.put(Constants.MESSAGE_ATTR, "Sorry, you do not have access to this resource.");
        map.put(Constants.CODE_ATTR, "403");
        return "exception_page";
    }
}
