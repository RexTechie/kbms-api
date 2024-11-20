package space.rexhub.kbms.bussiness.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import space.rexhub.kbms.bussiness.model.entity.UserEntity;

/**
 * Description: 用户dao层
 */
public interface UserMapper extends BaseMapper<UserEntity> {

  /**
   * 根据角色id获取角色名称
   * @param roleId 角色id
   * @return 角色名称
   */
  String getRoleName(Integer roleId);
}




