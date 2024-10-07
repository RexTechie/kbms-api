package space.rexhub.kbms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import space.rexhub.kbms.bussiness.service.UserService;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.constant.Constant;
import space.rexhub.kbms.common.model.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 自定义登出成功处理器
 *
 * @author Rex
 * @date 2021-11-05 15:23
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler{


  @Autowired
  private UserService userService;

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    String token = request.getHeader(Constant.ADMIN_AUTHORIZATION);
    Long userId = userService.getUidByToken(token);
    if (userId != null){
      userService.logout(token);
      Result.success(response, CommonStatus.SUCCESS);
    }else{
      Result.error(response, CommonStatus.NOT_LOGIN);
    }
  }
}
