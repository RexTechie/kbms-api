package space.rexhub.kbms.bussiness.model.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import space.rexhub.kbms.common.model.dto.Pagination;

import javax.validation.constraints.Pattern;


/**
 * Description: 用户分页
 *
 * @author Rex
 * @date 2023-07-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageForm extends Pagination {

    /**
     * 用户名模糊匹配
     */
    @Pattern(regexp = "^[0-9a-zA-Z_]{0,10}$", message = "用户名称格式不正确，需有1-10位的字母、数字、下划线组成")
    private String username;

    /**
     * 用户角色id
     */
    @Range(min = 1, max = 2, message = "角色格式有误")
    private Integer roleId;
}
