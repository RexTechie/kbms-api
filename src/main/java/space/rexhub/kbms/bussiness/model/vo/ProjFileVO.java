package space.rexhub.kbms.bussiness.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Description: 文件分页数据
 *
 * @author Rex
 * @date 2023-07-04
 */
@Data
public class ProjFileVO {

    /**
     * 文件id
     */
    private Long id;

    /**
     * 关联项目id
     */
    private Long projId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件后缀
     */
    private String suffix;

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
