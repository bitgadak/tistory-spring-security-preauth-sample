package org.example.tistorysecuritypreauthsample.config.auth;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Order(2)
@Component
public class UserAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

  public static final String USER_ID_HEADER = "USER-ID-HEADER";
  public static final String USER_ROLES_HEADER = "USER-ROLES-HEADER";

  public UserAuthenticationFilter(UserAuthenticationProvider userAuthenticationProvider) {
    super.setCheckForPrincipalChanges(true);
    super.setAuthenticationManager(new ProviderManager(userAuthenticationProvider));
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    String userId = request.getHeader(USER_ID_HEADER);
    List<String> roles = Collections.list(request.getHeaders(USER_ROLES_HEADER));

    if (ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(roles)) {
      return null;
    }

    return Map.of(
        "userId", userId,
        "roles", roles);
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "";
  }

  @Override
  protected boolean principalChanged(HttpServletRequest request,
      Authentication currentAuthentication) {

    if (currentAuthentication instanceof PreAuthenticatedAuthenticationToken) {
      return false;
    }

    return super.principalChanged(request, currentAuthentication);
  }
}
