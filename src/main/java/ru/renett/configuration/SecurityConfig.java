package ru.renett.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.renett.controllers.exception.CustomAccessDeniedHandler;
import ru.renett.controllers.exception.RestAccessDeniedHandler;
import ru.renett.models.Role;
import ru.renett.models.User;
import ru.renett.security.oauth.VkAuthenticationProvider;
import ru.renett.security.oauth.VkOauthAuthenticationFilter;
import ru.renett.security.rest.JwtTokenAuthenticationFilter;
import ru.renett.security.rest.JwtTokenAuthorizationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Configuration
    @Order(2)
    public static class MainConfiguration extends WebSecurityConfigurerAdapter {
        public final UserDetailsService userDetailsService;
        public final PasswordEncoder passwordEncoder;
        public final VkAuthenticationProvider vkOauthProvider;
        public final VkOauthAuthenticationFilter vkOauthFilter;
        public final CustomAccessDeniedHandler accessDeniedHandler;
        public final DataSource dataSource;

        public MainConfiguration(@Qualifier("MainUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, VkAuthenticationProvider vkOauthProvider, VkOauthAuthenticationFilter vkOauthFilter, CustomAccessDeniedHandler accessDeniedHandler, DataSource dataSource) {
            this.userDetailsService = userDetailsService;
            this.passwordEncoder = passwordEncoder;
            this.vkOauthProvider = vkOauthProvider;
            this.vkOauthFilter = vkOauthFilter;
            this.accessDeniedHandler = accessDeniedHandler;
            this.dataSource = dataSource;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
            auth.authenticationProvider(vkOauthProvider);
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .authorizeRequests()
                        .antMatchers("/signUp").permitAll()
                        .antMatchers("/profile/").authenticated()
                        .antMatchers("/profile/edit").authenticated()
                        .antMatchers("/confirm").hasAuthority(User.State.NOT_CONFIRMED.name()) // todo CONFIRMATION OF ACCOUNTS BY EMAIL
                        .antMatchers("/admin/**").hasRole(Role.ROLE.ADMIN.name())
                        .antMatchers("/articles/*/edit").authenticated()
                        .antMatchers("/articles/new").authenticated()
                        .antMatchers("/articles/*/comments").authenticated()
                        .antMatchers("/", "/main", "/resources/**", "/articles").permitAll()
                        .antMatchers("/ajax/**").authenticated()
                        .anyRequest().permitAll()
                        .and()
                    .formLogin()
                        .loginPage("/signIn")
                        .defaultSuccessUrl("/profile")
                        .usernameParameter("userName")
                        .passwordParameter("password")
                        .failureUrl("/signIn?error=true")
                        .permitAll()
                        .and()
                    .addFilterBefore(vkOauthFilter, UsernamePasswordAuthenticationFilter.class)
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
                        .tokenValiditySeconds(Constants.SECURITY_TOKEN_VALIDITY_SECONDS)
                    .and()
                        .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        }

        @Bean
        public PersistentTokenRepository tokenRepository() {
            JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
            jdbcTokenRepository.setDataSource(dataSource);
            return jdbcTokenRepository;
        }
    }

    @Configuration
    @Order(1)
    public static class RestApiConfiguration extends WebSecurityConfigurerAdapter {
        public final UserDetailsService userDetailsService;
        public final PasswordEncoder passwordEncoder;
        public final RestAccessDeniedHandler accessDeniedHandler;

        @Value("${jwt.secret-key}")
        public String secretKey;

        @Value("${jwt.expires-in}")
        public long expiresIn;

        public static final String API_URL_PREFIX = "/api";
        public static final String API_LOGIN_URL = API_URL_PREFIX + "/authorization";
        public static final String API_HEADER_PREFIX = "Bearer ";

        @Autowired
        private ObjectMapper objectMapper;

        public RestApiConfiguration(@Qualifier("MainUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, RestAccessDeniedHandler accessDeniedHandler) {
            this.userDetailsService = userDetailsService;
            this.passwordEncoder = passwordEncoder;
            this.accessDeniedHandler = accessDeniedHandler;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().ignoringAntMatchers(API_URL_PREFIX + "/**");
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilter(jwtTokenAuthenticationFilter())
                    .addFilterBefore(jwtTokenAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
            http.authorizeRequests()
                    .antMatchers(API_LOGIN_URL).permitAll()
                    .antMatchers(API_URL_PREFIX + "/articles").authenticated()
                    .and()
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler);;
        }

        @Bean
        public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter() throws Exception {
            JwtTokenAuthenticationFilter authenticationFilter = new JwtTokenAuthenticationFilter(authenticationManagerBean(), secretKey, objectMapper, expiresIn);
            authenticationFilter.setFilterProcessesUrl(API_LOGIN_URL);

            return authenticationFilter;
        }

        @Bean
        public JwtTokenAuthorizationFilter jwtTokenAuthorizationFilter() {
            return new JwtTokenAuthorizationFilter(objectMapper, secretKey, expiresIn);
        }
    }
}
