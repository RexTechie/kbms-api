package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import space.rexhub.kbms.common.validation.Add;
import space.rexhub.kbms.common.validation.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Description: 标签表单
 *
 * @author Rex
 * @date 2023-07-04
 */
@Data
public class TagForm {

    /**
     * 用户id
     */
    @NotNull(groups = Update.class, message = "标签id不能为空")
    @Null(groups = Add.class, message = "标签id需为空")
    private Long id;

    /**
     * 标签名称不能为空
     */
    @NotBlank(message = "标签名称不能为空", groups = {Add.class, Update.class})
    @Length(max = 100, message = "标签长度不大于100", groups = {Add.class, Update.class})
    private String name;

    /**
     * 父级id
     */
    @NotNull(message = "标签父级id", groups = {Add.class, Update.class})
    private Long parentId;

    /**
     * 1-一级标签，2-二级标签，3-三级标签
     */
    @NotNull(message = "标签等级", groups = {Add.class, Update.class})
    @Range(min = 1, max = 3, message = "标签不能超过三级")
    private Byte level;
}
