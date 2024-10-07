package space.rexhub.kbms.bussiness.model.vo;

import lombok.Data;

/**
 * Description: 项目成员
 *
 * @author Rex
 * @date 2024-03-20
 */
@Data
public class ProjMemberVO {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 角色：所有者，参与者
     */
    private String type;
}
