package org.example.tistorysecuritypreauthsample.controller;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  @GetMapping("/sample")
  public String sample(Authentication authentication) {
    print(authentication);
    return "SUCCESS";
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping("/sample-user")
  public String sampleUser(Authentication authentication) {
    print(authentication);
    return "SUCCESS";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/sample-admin")
  public String sampleAdmin(Authentication authentication) {
    print(authentication);
    return "SUCCESS";
  }

  @PreAuthorize("hasRole('SYSTEM')")
  @GetMapping("sample-system")
  public String sampleSystem(Authentication authentication) {
    print(authentication);
    return "SUCCESS";
  }

  private void print(Authentication authentication) {
    if (authentication != null) {
      Map<?, ?> info = (Map<?, ?>) authentication.getPrincipal(); // @AuthenticationPrincipal
      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
      System.out.println("ID=" + info.get("id") + ", ROLE=" + authorities.toString());
    } else {
      System.out.println("unauthenticated");
    }
  }
}
