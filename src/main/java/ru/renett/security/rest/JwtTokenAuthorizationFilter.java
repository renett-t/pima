package ru.renett.security.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.renett.configuration.SecurityConfig;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final String secretKey;
    private final long expiresIn;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(SecurityConfig.API_LOGIN_URL)) {
            filterChain.doFilter(request, response);
        } else if (!checkUriContainsApiPrefix(request.getRequestURI())) {
            log.warn("Not api request");
            filterChain.doFilter(request, response);
        } else {
                String header = request.getHeader("Authorization");
                if (header == null || !header.startsWith(SecurityConfig.API_HEADER_PREFIX)) {
                    log.warn("Attempt auth failed. Header = " + header);
                    filterChain.doFilter(request, response);
                } else {
                    String token = header.substring(SecurityConfig.API_HEADER_PREFIX.length());

                    try {
                        DecodedJWT decodedJWT = Jwt.require(Algorithm.HMAC256(secretKey))
                                .build().verify(token);

                        Long expirationTime = decodedJWT.getClaim("expires_at").asLong();
                        if (Calendar.getInstance().getTimeInMillis() + expiresIn > expirationTime) {
                            log.warn("Attempt auth failed. Token expired");
                            sendTokenExpiredResult(response);
                        } else {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(token, null,
                                            Collections.singletonList(
                                                    new SimpleGrantedAuthority(decodedJWT.getClaim("role").asString())));
                            authenticationToken.setAuthenticated(true);
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            filterChain.doFilter(request, response);
                        }
                    } catch (JWTVerificationException e) {
                        log.warn("Attempt auth failed. Token invalid");
                        sendForbiddenResult(response);
                    }
                }

            }
        }

    private boolean checkUriContainsApiPrefix(String requestURI) {
        return requestURI.contains(SecurityConfig.API_URL_PREFIX);
    }

    private void sendForbiddenResult(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("error", "Invalid token"));

    }

    private void sendTokenExpiredResult(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("error", "Token is expired"));
    }
}
