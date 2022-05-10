package ru.renett.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.renett.dto.SignUpDto;
import ru.renett.exceptions.ServiceException;
import ru.renett.service.impl.UserServiceImpl;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signUp")
public class RegistrationController {
    private final UserServiceImpl userService;

    private static final String SIGN_UP_FORM = "signUpForm";

    @GetMapping
    public String registration(Model model) {
        model.addAttribute(SIGN_UP_FORM, new SignUpDto());
        return "sign_up";
    }

    @PostMapping
    public String addUser(@ModelAttribute @Valid SignUpDto signUpForm, BindingResult bindingResult, ModelMap map) {
        System.out.println(signUpForm);

        if (bindingResult.hasErrors()) {
            map.put(SIGN_UP_FORM, signUpForm);
            return "sign_up";
        }

        try {
            userService.signUp(userService);
        } catch (ServiceException ex) {
            map.put("message", ex.getMessage());
            return "sign_up";
        }

        return "redirect:/signIn";
    }
}
