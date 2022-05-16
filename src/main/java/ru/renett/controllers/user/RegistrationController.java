package ru.renett.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.renett.dto.form.SignUpForm;
import ru.renett.exceptions.ServiceException;
import ru.renett.service.user.UsersService;

import javax.validation.Valid;

import static ru.renett.configuration.Constants.SIGN_UP_FORM_ATTR;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signUp")
public class RegistrationController {
    private final UsersService usersService;


    @GetMapping
    public String registration(Model model) {
        model.addAttribute(SIGN_UP_FORM_ATTR, new SignUpForm());
        return "sign_up";
    }

    @PostMapping
    public String addUser(@ModelAttribute @Valid SignUpForm signUpForm, BindingResult bindingResult, ModelMap map) {
        System.out.println(signUpForm);

        if (bindingResult.hasErrors()) {
            System.out.println("Error Signing up");
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            map.put(SIGN_UP_FORM_ATTR, signUpForm);
            return "sign_up";
        }

        try {
            usersService.signUp(signUpForm);
        } catch (ServiceException ex) {
            map.put("message", ex.getMessage());
            return "sign_up";
        }

        return "redirect:/signIn";
    }
}
