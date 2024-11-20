package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;
import space.rexhub.kbms.common.validation.Add;
import space.rexhub.kbms.common.validation.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Description: 用户注册表
 *
 */
@Data
public class ProjForm {

    /**
     * 用户id
     */
    @Null(groups = Add.class, message = "项目id需为空")
    @NotNull(groups = Update.class, message = "项目id不能为空")
    private Long id;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空", groups = Add.class)
    private String title;

    /**
     * 项目图标地址
     */
    private String img;

    /**
     * 简介
     */
    private String description;

    /**
     * 项目状态
     */
    private Byte status;
}
