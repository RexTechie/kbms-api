package space.rexhub.kbms.bussiness.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Description: 文档分页数据
 *
 * @author Rex
 * @date 2024-04-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocDetailVO extends DocVO{

    /**
     * 内容（文本形式）
     */
    private String contentText;

    /**
     * 内容（html）形式
     */
    private String contentHtml;

}
