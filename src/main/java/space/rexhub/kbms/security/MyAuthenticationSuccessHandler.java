package space.rexhub.kbms.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import space.rexhub.kbms.bussiness.service.UserService;
import space.rexhub.kbms.common.model.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 登录成功处理器
 *
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  private UserService userService;

  /**
   * Called when a user has been successfully authenticated.
   *
   * @param request        the request which caused the successful authentication
   * @param response       the response
   * @param authentication the <tt>Authentication</tt> object which was created during
   */
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    AdminUser user = (AdminUser)authentication.getPrincipal();
    String token = userService.login(user.getId());
    Map<String, Object> res = new HashMap<>(2);
    res.put("token", token);
    res.put("role", userService.getUserAuthorityInfo(user.getId()));
    res.put("username", user.getUsername());
    log.info("登录成功");
    Result.success(response, res);
  }
}
