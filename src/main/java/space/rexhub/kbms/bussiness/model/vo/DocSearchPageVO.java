package space.rexhub.kbms.bussiness.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description: Es文档分页实体类
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocSearchPageVO extends Page<DocSearchVO> {
    private Long duration;
}
