package space.rexhub.kbms.bussiness.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Description: 用户分页数据
 *
 */
@Data
public class ProjVO {

    /**
     * 项目id
     */
    private Long id;

    /**
     * 项目标题
     */
    private String title;

    /**
     * 项目简介
     */
    private String description;

    /**
     * 项目图标地址
     */
    private String img;

    /**
     * 项目状态，1-未开始，2-进行中，3-已暂停，4-已完成，5-已取消
     */
    private Integer status;

    /**
     * 创建者
     */
    private String owner;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
