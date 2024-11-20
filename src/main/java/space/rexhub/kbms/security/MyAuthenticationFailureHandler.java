package space.rexhub.kbms.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.model.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 登录失败处理器
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
  /**
   * Called when an authentication attempt fails.
   *
   * @param request   the request during which the authentication attempt occurred.
   * @param response  the response.
   * @param exception the exception which was thrown to reject the authentication
   */
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
    log.error("======AuthenticationException Class: {}", exception.toString());
    log.error("======AuthenticationException Msg: {}", exception.getMessage());
    log.error("======AuthenticationException: ", exception);
    Result.error(response, CommonStatus.LOGIN_ERROR);
  }
}
