package space.rexhub.kbms.bussiness.model.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import space.rexhub.kbms.base.BaseEntity;

/**
 * Description: 项目实体类
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_proj")
public class ProjEntity extends BaseEntity {

    /**
     * 项目标题
     */
    private String title;

    /**
     * 项目简介
     */
    private String description;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 项目图标地址
     */
    private String img;

    /**
     * 项目状态，1-未开始，2-进行中，3-已暂停，4-已完成，5-已取消
     */
    private Byte status;

    /**
     * 软删除，0-未删除，1-已删除
     */
    private Byte deleted;

}
