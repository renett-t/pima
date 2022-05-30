package ru.renett.security.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.renett.configuration.SecurityConfig;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final String secretKey;
    private final long expiresIn;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(SecurityConfig.RestApiConfiguration.API_LOGIN_URL)) {
            filterChain.doFilter(request, response);
        } else if (!checkUriContainsApiPrefix(request.getRequestURI())) {
            log.warn("Not api request");
            filterChain.doFilter(request, response);
        } else {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith(SecurityConfig.RestApiConfiguration.API_HEADER_PREFIX)) {
                log.warn("Attempt auth failed. Header = " + header);
                filterChain.doFilter(request, response);
            } else {
                String token = header.substring(SecurityConfig.RestApiConfiguration.API_HEADER_PREFIX.length());


                SignatureAlgorithm sa = HS256;
                SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
                String[] chunks = token.split("\\.");

                String tokenWithoutSignature = chunks[0] + "." + chunks[1];
                String signature = chunks[2];

                DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);

                if (!validator.isValid(tokenWithoutSignature, signature)) {
                    log.warn("Attempt auth failed. Token invalid");
                    sendForbiddenResult(response);
                }

                Base64.Decoder decoder = Base64.getUrlDecoder();
                String payload = new String(decoder.decode(chunks[1]));
                System.out.println(payload);

                JwtEntity data = objectMapper.readValue(payload, JwtEntity.class);

                log.debug(data.toString());
                log.warn(data.toString());
                long expirationTime = Long.parseLong(data.getExpires_at());
                long now = Calendar.getInstance().getTimeInMillis();
                log.warn("now - " + now);
                if (expirationTime < now) {
                    log.warn("Attempt auth failed. Token expired");
                    sendTokenExpiredResult(response);
                } else {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(token, null,
                                    Collections.singletonList(new SimpleGrantedAuthority(data.getRole())));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }
            }
        }
    }

    private boolean checkUriContainsApiPrefix(String requestURI) {
        return requestURI.contains(SecurityConfig.RestApiConfiguration.API_URL_PREFIX);
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
