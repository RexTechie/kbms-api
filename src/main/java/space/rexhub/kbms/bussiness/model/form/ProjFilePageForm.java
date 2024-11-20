package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import space.rexhub.kbms.common.model.dto.Pagination;

import javax.validation.constraints.NotNull;

/**
 * Description: 项目文件分页参数
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjFilePageForm extends Pagination {

    /**
     * 项目id
     */
    @NotNull(message = "项目id不能为空")
    private Long projId;

    /**
     * 文件名模糊匹配
     */
    private String fileName;

}
