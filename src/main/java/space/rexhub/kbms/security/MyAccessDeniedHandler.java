package space.rexhub.kbms.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.model.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 自定义权限访问失败的处理器
 *
 * @author Rex
 * @date 2021-11-05 15:26
 */
@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
  /**
   * Handles an access denied failure.
   *
   * @param request               that resulted in an <code>AccessDeniedException</code>
   * @param response              so that the user agent can be advised of the failure
   * @param accessDeniedException that caused the invocation
   * @throws IOException      in the event of an IOException
   */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    log.error("权限不足");
    Result.error(response, CommonStatus.NOT_ALLOWED);
  }
}
