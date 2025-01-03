package org.example.library.security;

import lombok.RequiredArgsConstructor;
import org.example.library.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var jwtAuth = (JwtTokenAuthentication) authentication;
        requireValidToken(jwtAuth.getToken());
        var user = jwtUtil.getUserByToken(jwtAuth.getToken());
        return new JwtTokenAuthentication(jwtAuth.getToken(), new UserDetailsImpl(user));
    }

    private void requireValidToken(String token) {
        if (!jwtUtil.validateJwtToken(token)) {
            throw new BadCredentialsException("Invalid JWT token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtTokenAuthentication.class.equals(authentication);
    }
}
