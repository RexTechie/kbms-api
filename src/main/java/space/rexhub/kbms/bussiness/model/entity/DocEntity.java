package space.rexhub.kbms.bussiness.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import space.rexhub.kbms.base.BaseEntity;

/**
 * Description: 项目文档实体类
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_doc")
public class DocEntity extends BaseEntity {

    /**
     * 项目id
     */
    private Long projId;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档摘要
     */
    private String summary;

    /**
     * 文档内容，文本类型
     */
    private String contentText;

    /**
     * 文档内容，html类型
     */
    private String contentHtml;

    /**
     * 是否为文件上传形式，0-非文件上传形式，1-文件上传形式
     */
    private Byte isUpload;

    /**
     * 软删除，0-未删除，1-已删除
     */
    private Byte deleted;
}
