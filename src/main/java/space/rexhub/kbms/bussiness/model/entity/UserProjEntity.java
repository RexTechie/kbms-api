package space.rexhub.kbms.bussiness.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import space.rexhub.kbms.base.BaseEntity;

/**
 * Description: TODO
 *
 * @author Rex
 * @date 2024-03-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_user_proj")
public class UserProjEntity extends BaseEntity {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 项目id
     */
    private Long projId;

    /**
     * 成员类型，0-所有者，1-参与者
     */
    private Byte type;
}
