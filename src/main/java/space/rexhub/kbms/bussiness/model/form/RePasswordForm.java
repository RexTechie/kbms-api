package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description: 修改密码
 *
 */
@Data
public class RePasswordForm {

    /**
     * 旧密码
     */
    @NotNull(message = "请输入旧密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotNull(message = "请输入新密码")
    private String newPassword;
}
