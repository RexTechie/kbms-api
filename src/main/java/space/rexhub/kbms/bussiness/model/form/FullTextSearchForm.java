package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import space.rexhub.kbms.common.model.dto.Pagination;

import javax.validation.constraints.NotNull;

/**
 * Description: 全文检索表单
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FullTextSearchForm extends Pagination {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 指定es或mysql方式查询
     */
    private String type;
}
