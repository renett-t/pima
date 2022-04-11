package ru.renett.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.renett.models.User;
import ru.renett.service.security.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final UserService userService;

    private static final String userForm = "userForm";

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute(userForm, new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute(userForm) @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userForm.getPassword().equals(userForm.getPasswordRepeat())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        // todo: exceptions handling
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }
}
