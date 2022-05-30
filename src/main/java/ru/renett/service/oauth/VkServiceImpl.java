package ru.renett.service.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.renett.dto.oauth.vk.TokenResponse;
import ru.renett.dto.oauth.vk.VkUserResponse;
import ru.renett.exceptions.OauthRequestException;
import ru.renett.models.Role;
import ru.renett.models.User;
import ru.renett.repository.UsersRepository;
import ru.renett.repository.VkRepository;
import ru.renett.utils.RolesCache;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class VkServiceImpl implements VkService {
    private final VkRepository vkRepository;
    private final UsersRepository usersRepository;
    private final RolesCache rolesCache;

    @Override
    public User getUserFromVkByCode(String code) {
        try {
            TokenResponse response = vkRepository.getToken(code);

            Optional<User> userFromDb = usersRepository.findUserByEmail(response.getEmail());
            if (userFromDb.isPresent()) {
                return userFromDb.get();
            } else {
                VkUserResponse vkUser = vkRepository.getUserData(response.getId(), response.getAccessToken());

                User user = User.builder()
                        .firstName(vkUser.getFirstName())
                        .secondName(vkUser.getSecondName())
                        .email(response.getEmail())
                        .userName(response.getEmail())
                        .password("12345")         // todo: i think it's not good at all...
                        .roles(Set.of(rolesCache.getRoleByName(Role.ROLE.USER)))
                        .state(User.State.CONFIRMED)
                        .build();

                return usersRepository.save(user);
            }
        } catch (OauthRequestException e) {
            throw new UsernameNotFoundException("Vk authorization failed. User is not found.");
        }
    }
}
