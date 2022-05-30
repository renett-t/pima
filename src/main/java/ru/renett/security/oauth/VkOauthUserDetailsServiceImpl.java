package ru.renett.security.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.renett.models.User;
import ru.renett.security.UserDetailsImpl;
import ru.renett.service.oauth.VkService;

@Service("VkOauthUserDetailsService")
@RequiredArgsConstructor
public class VkOauthUserDetailsServiceImpl implements UserDetailsService {

    private final VkService vkService;

    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        User userFromVkOrDb = vkService.getUserFromVkByCode(code);
        return new UserDetailsImpl(userFromVkOrDb);
    }
}
