package ru.renett.security.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.renett.dto.rest.AuthDto;
import ru.renett.security.UserDetailsImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

@Slf4j
public class JwtTokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final String secretKey;
    private final ObjectMapper objectMapper;
    private final long expiresIn;
    

    public JwtTokenAuthenticationFilter(AuthenticationManager manager, String secretKey, ObjectMapper objectMapper, long expiresIn) {
//        super(manager);
        this.secretKey = secretKey;
        this.objectMapper = objectMapper;
        this.expiresIn = expiresIn;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthDto authDto = objectMapper.readValue(request.getReader(), AuthDto.class);

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword());

            return super.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            log.warn("Error attempting to read request data. " + e.getMessage());
            throw new AuthenticationServiceException("Unable to read request data", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authResult.getPrincipal();

        long current = Calendar.getInstance().getTimeInMillis();

        System.out.println(user.getRoles().toString());
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("email", user.getUsername())
                .withClaim("role", user.getRoles().toString())
                .withClaim("state", user.getState())
                .withClaim("expires_at", current + expiresIn)
                .sign(Algorithm.HMAC256(secretKey));

        objectMapper.writeValue(response.getWriter(),
                Map.of("token", token,
                        "expires_in", String.valueOf(expiresIn)));

    }
}
