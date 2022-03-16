package org.example.tistorysecuritypreauthsample;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTest {

  @Autowired
  private MockMvc mvc;

  // 1. 권한없는요청

  @Test
  public void 권한없는요청_제한없는메소드_접근가능() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample"));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().string("SUCCESS"));
  }

  @Test
  public void 권한없는요청_사용자권한메소드_접근불가() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-user"));

    resultActions
        .andExpect(status().isForbidden());
  }

  @Test
  public void 권한없는요청_관리자권한메소드_접근불가() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-admin"));

    resultActions
        .andExpect(status().isForbidden());
  }

  @Test
  public void 권한없는요청_시스템권한메소드_접근불가() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-system"));

    resultActions
        .andExpect(status().isForbidden());
  }

  // 2. 일반사용자요청

  @Test
  public void 일반사용자요청_제한없는메소드_접근가능() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample")
            .header("USER-ID-HEADER", "tester")
            .header("USER-ROLES-HEADER", "USER"));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().string("SUCCESS"));
  }

  @Test
  public void 일반사용자요청_사용자권한메소드_접근가능() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-user")
            .header("USER-ID-HEADER", "tester")
            .header("USER-ROLES-HEADER", "USER"));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().string("SUCCESS"));
  }

  @Test
  public void 일반사용자요청_관리자권한메소드_접근불가() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-admin")
            .header("USER-ID-HEADER", "tester")
            .header("USER-ROLES-HEADER", "USER"));

    resultActions
        .andExpect(status().isForbidden());
  }

  @Test
  public void 일반사용자요청_시스템권한메소드_접근불가() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-system")
            .header("USER-ID-HEADER", "tester")
            .header("USER-ROLES-HEADER", "USER"));

    resultActions
        .andExpect(status().isForbidden());
  }

  // 3. 관리자요청

  @Test
  public void 관리자요청_제한없는메소드_접근가능() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample")
            .header("USER-ID-HEADER", "admin")
            .header("USER-ROLES-HEADER", "USER")
            .header("USER-ROLES-HEADER", "ADMIN"));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().string("SUCCESS"));
  }

  @Test
  public void 관리자요청_사용자권한메소드_접근가능() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-user")
            .header("USER-ID-HEADER", "admin")
            .header("USER-ROLES-HEADER", "USER")
            .header("USER-ROLES-HEADER", "ADMIN"));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().string("SUCCESS"));
  }

  @Test
  public void 관리자요청_관리자권한메소드_접근가능() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-admin")
            .header("USER-ID-HEADER", "admin")
            .header("USER-ROLES-HEADER", "USER")
            .header("USER-ROLES-HEADER", "ADMIN"));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().string("SUCCESS"));
  }

  @Test
  public void 관리자요청_시스템권한메소드_접근불가() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-system")
            .header("USER-ID-HEADER", "admin")
            .header("USER-ROLES-HEADER", "USER")
            .header("USER-ROLES-HEADER", "ADMIN"));

    resultActions
        .andExpect(status().isForbidden());
  }

  // 4. 시스템요청

  @Test
  public void 시스템요청_제한없는메소드_접근가능() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample")
            .header("SYSTEM-AUTH-HEADER", "password"));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().string("SUCCESS"));
  }

  @Test
  public void 시스템요청_사용자권한메소드_접근불가() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-user")
            .header("SYSTEM-AUTH-HEADER", "password"));

    resultActions
        .andExpect(status().isForbidden());
  }

  @Test
  public void 시스템요청_관리자권한메소드_접근불가() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-admin")
            .header("SYSTEM-AUTH-HEADER", "password"));

    resultActions
        .andExpect(status().isForbidden());
  }

  @Test
  public void 시스템요청_시스템권한메소드_접근가능() throws Exception {
    ResultActions resultActions = mvc
        .perform(get("/sample-system")
            .header("SYSTEM-AUTH-HEADER", "password"));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().string("SUCCESS"));
  }
}
