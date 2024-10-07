package space.rexhub.kbms.bussiness.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import space.rexhub.kbms.bussiness.model.dto.TagDetailDTO;

import java.util.List;

/**
 * Description: 知识点VO
 *
 * @author Rex
 * @date 2023-10-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TagTreeVO extends TagDetailDTO {

    /**
     * 下级知识点
     */
    private List<TagTreeVO> children;
}
