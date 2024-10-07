package space.rexhub.kbms.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Description: 重写登录方法
 *
 * @author Rex
 */
@Slf4j
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    log.info("===========================登录验证=======================");
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map;
    try(InputStream is = request.getInputStream()) {
      map = mapper.readValue(is,Map.class);
    }catch (IOException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("参数有误");
    }
    String username = map.get("username");
    String password = map.get("password");
    logger.info("username:" + username);
    logger.info("password:" + password);

    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
    // 为detail赋值
    setDetails(request, authRequest);
    return this.getAuthenticationManager().authenticate(authRequest);

  }

}
