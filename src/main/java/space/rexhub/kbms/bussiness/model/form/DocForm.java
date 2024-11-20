package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;
import space.rexhub.kbms.common.validation.Add;
import space.rexhub.kbms.common.validation.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Description: 文档表单
 *
 */
@Data
public class DocForm {

    /**
     * 文档id
     */
    @Null(groups = Add.class, message = "文档id需为空")
    @NotNull(groups = Update.class, message = "文档id不能为空")
    private Long id;

    /**
     * 项目id
     */
    @NotNull(groups = {Add.class, Update.class}, message = "项目id不能为空")
    private Long projId;

    /**
     * 文档标题
     */
    @NotBlank(message = "项目名称不能为空", groups = Add.class)
    private String title;

    /**
     * 文档摘要
     */
    private String summary;

    /**
     * 文档内容，文本形式
     */
    private String contentText;

    /**
     * 文档内容，html格式
     */
    private String contentHtml;

}
