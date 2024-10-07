package space.rexhub.kbms.bussiness.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 用户详情
 *
 * @author Rex
 * @date 2021-11-03 13:08
 */
@Data
public class UserDetailVO implements Serializable {
  private final static long serialVersionUID = -1L;

  /**
   * 用户id
   */
  private Long id;

  /**
   * 密码
   */
  private String password;

  /**
   * 用户名
   */
  private String username;

  /**
   * 用户昵称
   */
  private String nickName;

  /**
   * 登陆token
   */
  private String token;

  /**
   * 用户角色
   */
  private String role;

  /**
   * 登陆时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date loginTime;
}
