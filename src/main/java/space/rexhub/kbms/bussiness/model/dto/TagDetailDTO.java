package space.rexhub.kbms.bussiness.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Description: 知识点分页数据
 *
 * @author Rex
 * @date 2023-07-08
 */
@Data
public class TagDetailDTO {
    /**
     * 标签id
     */
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 级别
     */
    private Byte level;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
