package space.rexhub.kbms.bussiness.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import space.rexhub.kbms.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;


/**
 * Description: 用户实体类
 *
 * @author Rex
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_user")
public class UserEntity extends BaseEntity implements Serializable {


    /**
    * 用户名
    */
    private String username;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
    * 用户密码
    */
    private String password;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 登陆token
     */
    private String token;

    /**
     * 登陆时间
     */
    private Date loginTime;

}
