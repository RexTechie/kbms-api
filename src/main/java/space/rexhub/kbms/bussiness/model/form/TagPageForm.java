package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import space.rexhub.kbms.common.model.dto.Pagination;

/**
 * Description: 知识点表单
 *
 * @author Rex
 * @date 2023-07-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TagPageForm extends Pagination {

    /**
     * 标签名称不能为空
     */
    @Length(max = 100, message = "标签名称输入过长")
    private String name;
}
