package ru.renett.controllers.user.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth")
public class OauthController {

    @GetMapping("/vk")
    public String registerWithVk(@RequestParam String code) {
        // authorization callback from vk.
        return "redirect:/profile";
    }
}
