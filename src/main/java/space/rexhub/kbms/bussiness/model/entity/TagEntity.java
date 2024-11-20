package space.rexhub.kbms.bussiness.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import space.rexhub.kbms.base.BaseEntity;

/**
 * Description: 知识点
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_tag")
public class TagEntity extends BaseEntity {

    /**
     * 知识点名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 父级知识点id，为-1则无父级
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 1-一级知识点，2-二级知识点，3-三级知识点
     */
    private Byte level;
}
