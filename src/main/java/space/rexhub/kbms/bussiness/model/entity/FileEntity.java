package space.rexhub.kbms.bussiness.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import space.rexhub.kbms.base.BaseEntity;

/**
 * Description: 文件实体类
 *
 * @author Rex
 * @date 2024-04-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_file")
public class FileEntity extends BaseEntity {

    /**
     * 项目id
     */
    private Long projId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件后缀名
     */
    private String suffix;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 软删除，0-未删除，1-已删除
     */
    private Byte deleted;
}
