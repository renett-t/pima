package ru.renett.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.repository.UsersRepository;

@Service("CustomUserDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.contains("@")) {
            return new UserDetailsImpl(
                    usersRepository.findUserByEmail(username)
                            .orElseThrow(() -> new EntityNotFoundException("User with email = " + username + "not found."))
            );
        } else {
            return new UserDetailsImpl(
                    usersRepository.findUserByUserName(username)
                            .orElseThrow(() -> new EntityNotFoundException("User with username = " + username + "not found."))
            );
        }
    }
}
