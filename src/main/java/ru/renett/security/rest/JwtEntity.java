package ru.renett.security.rest;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtEntity {
    private String sub;
    private String role;
    private String expires_at;
    private String state;
    private String email;
}
