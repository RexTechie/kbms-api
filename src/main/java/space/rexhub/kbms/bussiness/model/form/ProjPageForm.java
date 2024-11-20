package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import space.rexhub.kbms.common.model.dto.Pagination;

import javax.validation.constraints.Pattern;


/**
 * Description: 项目分页表单
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjPageForm extends Pagination {

    /**
     * 标题模糊查询
     */
    private String title;

}
