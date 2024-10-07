package space.rexhub.kbms.bussiness.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * Description: 文档分页数据
 *
 * @author Rex
 * @date 2024-04-08
 */
@Data
public class DocVO {

    /**
     * 文档id
     */
    private Long id;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档摘要
     */
    private String summary;

    /**
     * 是否为文件上传形式，0-非文件上传形式，1-文件上传形式
     */
    private Byte isUpload;

    /**
     * 文档所有者
     */
    private String owner;

    /**
     * 文档创建时间
     */
    private Date createTime;

    /**
     * 文档更新时间
     */
    private Date updateTime;
}
