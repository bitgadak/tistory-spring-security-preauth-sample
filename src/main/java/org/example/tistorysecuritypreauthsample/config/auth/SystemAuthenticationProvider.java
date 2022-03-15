package org.example.tistorysecuritypreauthsample.config.auth;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class SystemAuthenticationProvider implements AuthenticationProvider {

  private static final String SECRET_KEY = "secret-key";

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String secretKey = (String) authentication.getPrincipal();

    if (secretKey == null || !secretKey.equals(SECRET_KEY)) {
      return null;
    }

    Map<String, Object> userInfo = Map.of("id", "system");
    List<SimpleGrantedAuthority> roles = Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_SYSTEM"));
    return new PreAuthenticatedAuthenticationToken(userInfo, null, roles);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
