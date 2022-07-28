package com.mgp.promo.user;

import com.mgp.promo.user.exception.UserNotPresentException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@AllArgsConstructor
public class UserSecurity {

    private final UserRepository userRepository;

    public boolean isEmailMatching(Authentication authentication, int id) {
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            User user;
            try {
                user = userRepository.findById(id).orElseThrow(() -> new UserNotPresentException(id));
            } catch (UserNotPresentException ex) {
                return false;
            }
            return user.getEmail().equals(jwt.getClaims().get("email"));
        }
        return false;
    }

}
