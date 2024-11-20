package space.rexhub.kbms.security;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import space.rexhub.kbms.bussiness.model.vo.UserDetailVO;
import space.rexhub.kbms.bussiness.service.UserService;
import space.rexhub.kbms.common.constant.Constant;
import space.rexhub.kbms.config.SecurityConfig;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Description: token校验机制
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

  @Value("${server.servlet.context-path}")
  private String contextPath;

  @Autowired
  private UserService userService;

  @Autowired
  private UserDetailServiceImpl userDetailService;

  public TokenAuthenticationFilter(AuthenticationManager authenticationManager){
    super(authenticationManager);
  }

  @SneakyThrows
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
    String token = request.getHeader(Constant.ADMIN_AUTHORIZATION);
    String uri = request.getRequestURI();
    AntPathMatcher matcher = new AntPathMatcher();
    for (String whiteUrl : SecurityConfig.URL_WHITELIST) {
      if (matcher.match(contextPath + whiteUrl, uri)){
        chain.doFilter(request, response);
        return;
      }
    }
    if (!StringUtils.hasText(token)){
      chain.doFilter(request, response);
      return;
    }
    Long userId = userService.getUidByToken(token);
    // 获取用户的权限等信息
    UserDetailVO user = userService.getUserDetail(userId);

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, userDetailService.getUserAuthorities(userId));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    chain.doFilter(request, response);
  }
}
