package space.rexhub.kbms.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.model.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 自定义认证异常处理器
 */
@Component
@Slf4j
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
  /**
   * Commences an authentication scheme.
   * <p>
   * <code>ExceptionTranslationFilter</code> will populate the <code>HttpSession</code>
   * attribute named
   * <code>AbstractAuthenticationProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY</code>
   * with the requested target URL before calling this method.
   * <p>
   * Implementations should modify the headers on the <code>ServletResponse</code> as
   * necessary to commence the authentication process.
   *
   * @param request       that resulted in an <code>AuthenticationException</code>
   * @param response      so that the user agent can begin authentication
   * @param authException that caused the invocation
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    log.error("认证失败，或token失效");
    Result.error(response, CommonStatus.NOT_LOGIN);
  }
}
