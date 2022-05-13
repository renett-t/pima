package ru.renett.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signIn")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping()
    public String getLoginPage(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/";
        }

        return "sign_in";
    }
}