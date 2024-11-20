package space.rexhub.kbms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import space.rexhub.kbms.bussiness.model.vo.UserDetailVO;
import space.rexhub.kbms.bussiness.service.UserService;

import java.util.List;

/**
 * Description: 用户信息管理服务类
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public AdminUser loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetailVO user = userService.getByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("用户名或密码不正确");
    }
    return new AdminUser(user.getId(), user.getUsername(), user.getPassword(), getUserAuthorities(user.getId()));
  }

  /**
   * 获取用户权限信息(角色，菜单权限)
   * @param userId 用户id
   * @return 权限信息
   */
  public List<GrantedAuthority> getUserAuthorities(Long userId){
    // 角色(ROLE_admin)、菜单操作权限(sys:user:list)
    //Role_admin,sys:user:list;
    String authority = userService.getUserAuthorityInfo(userId);
    return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
  }
}
