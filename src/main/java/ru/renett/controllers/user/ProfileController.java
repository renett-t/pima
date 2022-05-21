package ru.renett.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.UserDto;
import ru.renett.dto.form.SimpleUpdateUserForm;
import ru.renett.dto.form.UpdateUserForm;
import ru.renett.models.User;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.service.user.UsersService;

import java.util.List;

import static ru.renett.configuration.Constants.*;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UsersService usersService;
    private final ArticlesGetDataService articlesGetDataService;

    //     @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String getProfile(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        if (userDetails == null) {
            return "redirect:/signIn";
        } else {
            UserDto user = usersService.getUserByEmailOrUserName(userDetails.getUsername());
            map.put(USER_ATTR, user);
            List<ArticleDto> liked = articlesGetDataService.getLikedArticles(user.getId());
            map.put(LIKED_ARTICLES_ATTR, liked);
            return "profile";
        }
    }

    @GetMapping("/edit")
    public String getEditingProfilePage(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        UserDto user = usersService.getUserByEmailOrUserName(userDetails.getUsername());
        map.put(USER_ATTR, user);
        map.put(UPDATE_PROFILE_ATTR, new SimpleUpdateUserForm());
        return "profile_edit";
    }

    @PostMapping("/edit")
    public String edit(SimpleUpdateUserForm updateUserForm, @AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        System.out.println("\nupDAAAAAAAAAAAAAAAAAAAAATE\n\n");
        UserDto user = usersService.getUserByEmailOrUserName(userDetails.getUsername());
        UserDto updated = usersService.updateBasicUserData(updateUserForm, user.getId());
        map.put(USER_ATTR, updated);
        return "redirect:/profile";
    }

}
