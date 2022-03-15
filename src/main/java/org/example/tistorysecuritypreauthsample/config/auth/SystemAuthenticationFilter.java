package org.example.tistorysecuritypreauthsample.config.auth;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class SystemAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

  public static final String SYSTEM_AUTH_HEADER = "SYSTEM-AUTH-HEADER";

  public SystemAuthenticationFilter(SystemAuthenticationProvider systemAuthenticationProvider) {
    super.setCheckForPrincipalChanges(true);
    super.setAuthenticationManager(new ProviderManager(systemAuthenticationProvider));
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    return request.getHeader(SYSTEM_AUTH_HEADER);
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "";
  }
}
