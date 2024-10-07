package space.rexhub.kbms.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 * @author Rex
 * @date 2021-05-31 19:29
 */
@Data
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = -1L;

  /**
   * id编号，雪花算法随机生成
   */
  @TableId(type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * 创建时间
   */
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  /**
   * 更新时间
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  /**
   * 创建者
   */
  @TableField(fill = FieldFill.INSERT)
  private Long creator;

  /**
   * 更新者
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updater;
}
