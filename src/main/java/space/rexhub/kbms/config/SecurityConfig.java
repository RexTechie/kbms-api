package space.rexhub.kbms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import space.rexhub.kbms.security.*;


/**
 * Description: Spring Security配置
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailServiceImpl userDetailService;

  @Autowired
  private MyLogoutSuccessHandler logoutSuccessHandler;

  @Autowired
  private MyAccessDeniedHandler accessDeniedHandler;

  @Autowired
  private MyAuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private MyAuthenticationFailureHandler authenticationFailureHandler;

  @Autowired
  private MyAuthenticationSuccessHandler authenticationSuccessHandler;

  /**
   * 白名单
   */
  public static final String[] URL_WHITELIST = {
      "/static/**",
      "/test",
      "/login",
      "/logout",
      "/register",
     "/proj/file/download/**",
      "/proj/file/preview/**"
  };

  /**
   * 处理跨域问题
   * @return 跨域过滤器
   */
  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new      UrlBasedCorsConfigurationSource();
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.addAllowedOriginPattern("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(urlBasedCorsConfigurationSource);
  }

  @Bean
  TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
    return new TokenAuthenticationFilter(authenticationManager());
  }

  /**
   * 密码的加密形式
   * @return 加密器
   */
  @Bean
  BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  MyAuthenticationFilter myAuthenticationFilter() throws Exception{
    MyAuthenticationFilter filter = new MyAuthenticationFilter();
    // 登录验证成功处理器
    filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    // 登录验证失败处理器
    filter.setAuthenticationFailureHandler(authenticationFailureHandler);
    filter.setFilterProcessesUrl("/login");
    filter.setAuthenticationManager(authenticationManagerBean());
    return filter;

  }
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        // 登录配置
        .formLogin().loginPage("/")
        // 登出配置
        .and()
          .logout()
          .logoutUrl("/logout")
          .logoutSuccessHandler(logoutSuccessHandler)
        // 禁用session
        .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // 配置拦截规则
        .and()
          .authorizeRequests()
          .antMatchers(URL_WHITELIST).permitAll()
          .anyRequest().authenticated()
        // 异常处理器
        .and()
          .exceptionHandling()
          //认证异常处理器
          .authenticationEntryPoint(authenticationEntryPoint)
          //权限不足的处理器
          .accessDeniedHandler(accessDeniedHandler)
        // 配置自定义的过滤器
        .and()
          .addFilter(tokenAuthenticationFilter())
          .addFilterAt(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
    ;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailService);
  }

  public static void main(String[] args) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String encode = bCryptPasswordEncoder.encode("88888888");
    System.out.println(encode);
  }
}
