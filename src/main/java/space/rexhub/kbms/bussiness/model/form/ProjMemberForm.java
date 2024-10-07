package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description: 添加项目成员表单
 *
 * @author Rex
 * @date 2024-03-20
 */
@Data
public class ProjMemberForm {
    /**
     * 项目id
     */
    @NotNull( message = "项目id不能为空")
    private Long projId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
}
