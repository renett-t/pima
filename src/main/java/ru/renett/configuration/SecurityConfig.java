package ru.renett.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.renett.models.Role;
import ru.renett.models.User;
import ru.renett.security.oauth.VkAuthenticationProvider;
import ru.renett.security.oauth.VkOauthAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public final UserDetailsService userDetailsService;

    public final VkAuthenticationProvider vkOauthProvider;
    public final VkOauthAuthenticationFilter vkOauthFilter;
    public final DataSource dataSource;

    @Autowired
    public SecurityConfig(@Qualifier("MainUserDetailsService") UserDetailsService userDetailsService, VkAuthenticationProvider vkOauthProvider, VkOauthAuthenticationFilter vkOauthFilter, DataSource dataSource) {
        this.vkOauthFilter = vkOauthFilter;
        this.userDetailsService = userDetailsService;
        this.vkOauthProvider = vkOauthProvider;
        this.dataSource = dataSource;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(vkOauthProvider);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                    .ignoringAntMatchers("/api/**")
                    .and()
                .authorizeRequests()
                    .antMatchers("/signUp").permitAll()
                    .antMatchers("/profile/").authenticated()
                    .antMatchers("/profile/edit").authenticated()
                    .antMatchers("/confirm").hasAuthority(User.State.NOT_CONFIRMED.name()) //todo
                    .antMatchers("/admin/**").hasRole(Role.ROLE.ADMIN.name())
                    .antMatchers("/articles/*/edit").authenticated()
                    .antMatchers("/articles/new").authenticated()
                    .antMatchers("/articles/*/comments").authenticated()
                    .antMatchers("/", "/main", "/resources/**", "/articles").permitAll()
                    .antMatchers("/api/**").permitAll()
                    .antMatchers("/ajax/**").authenticated()
                .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .loginPage("/signIn")
                    .defaultSuccessUrl("/")
                    .usernameParameter("userName")
                    .passwordParameter("password")
                    .failureUrl("/signIn?error=true")
                    .permitAll()
                    .and()
                .addFilterAfter(vkOauthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                    .permitAll()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/signIn?logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .and()
                .rememberMe()
                    .rememberMeParameter("rememberMe")
                    .tokenRepository(tokenRepository())
                    .tokenValiditySeconds(Constants.SECURITY_TOKEN_VALIDITY_SECONDS);
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
