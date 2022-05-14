package ru.renett.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.renett.dto.form.UpdateUserForm;
import ru.renett.models.Article;
import ru.renett.models.User;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.service.user.UserService;

import java.util.List;

import static ru.renett.configuration.Constants.*;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final ArticlesGetDataService articlesGetDataService;

    //     @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String getProfile(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        if (userDetails == null) {
            return "redirect:/signIn";
        } else {
            User user = userService.getUserByEmailOrUserName(userDetails.getUsername());
            map.put(USER_ATTR, user);
            List<Article> liked = articlesGetDataService.getLikedArticles(user);
            map.put(LIKED_ARTICLES_ATTR, liked);
            return "profile";
        }
    }

    @GetMapping("/edit")
    public String getEditingProfilePage(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        User user = userService.getUserByEmailOrUserName(userDetails.getUsername());
        map.put(USER_ATTR, user);
        return "profile_edit";
    }

    @PostMapping("/edit")
    public String edit(UpdateUserForm updateUserForm, @AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        // todo: do i have to check id in update data and id in user get by UserDetails
        User user = userService.updateUserData(updateUserForm);
        map.put(USER_ATTR, user);
        return "redirect:/profile";
    }

}
