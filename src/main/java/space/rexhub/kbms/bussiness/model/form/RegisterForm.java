package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;
import space.rexhub.kbms.common.validation.Add;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * Description: 用户注册表
 *
 * @author Rex
 * @date 2023-07-04
 */
@Data
public class RegisterForm {

    /**
     * 用户id
     */
    @Null(groups = Add.class, message = "用户id需为空")
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = Add.class)
    @Pattern(regexp = "^[a-zA-Z0-9_]{2,20}$", message = "用户名称格式不正确，需有2-20位的字母、数字、下划线组成", groups = {Add.class})
    private String username;

    /**
     * 用户昵称不能为空
     */
    @NotBlank(message = "用户昵称不能为空", groups = Add.class)
    @Pattern(regexp = "^[a-zA-Z0-9_]{2,20}$", message = "用户昵称格式不正确，需有2-20位的字母、数字、下划线组成", groups = {Add.class})
    private String nickName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = Add.class)
    @Pattern(regexp = "^[a-zA-Z0-9_]{2,20}$", message = "密码格式不正确，需有2-20位的字母、数字、下划线组成", groups = {Add.class})
    private String password;
}
