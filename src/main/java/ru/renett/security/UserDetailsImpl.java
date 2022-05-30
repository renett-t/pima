package ru.renett.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.renett.models.Role;
import ru.renett.models.User;

import java.util.Collection;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private final String password;
    private final String username;
    private final String state;
    private final Set<Role> roles;

    public UserDetailsImpl(User user) {
        this.password = user.getPassword();
        this.username = user.getUserName();
        this.state = user.getState().name();
        this.roles = user.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !state.equals(User.State.BANNED.name()) && !state.equals(User.State.DELETED.name());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return state.equals(User.State.CONFIRMED.name());
    }
}
