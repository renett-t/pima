package ru.renett.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.renett.models.Role;
import ru.renett.models.User;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public final UserDetailsService userDetailsService;
    public final DataSource dataSource;

    @Autowired
    public SecurityConfig(@Qualifier("CustomUserDetailsService") UserDetailsService userDetailsService, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                    .ignoringAntMatchers("/api/**")
//                    .ignoringAntMatchers("/articles")
                    .and()
                .authorizeRequests()
                    .antMatchers("/signUp").permitAll()
                    .antMatchers("/profile").authenticated()
                    .antMatchers("/confirm").hasAuthority(User.State.NOT_CONFIRMED.name()) //todo
                    .antMatchers("/admin/**").hasRole(Role.ROLE.ADMIN.name())
                    .antMatchers("/articles/*/edit").hasRole(Role.ROLE.AUTHOR.name()) //todo
                    .antMatchers("/", "/main", "/resources/**", "/articles").permitAll()
                .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .loginPage("/signIn")
                    .defaultSuccessUrl("/")
                    .usernameParameter("userName")
                    .passwordParameter("password")
                    .failureUrl("/signIn?error")
                    .permitAll()
                    .and()
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
