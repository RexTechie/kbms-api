package space.rexhub.kbms.bussiness.dao;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import space.rexhub.kbms.bussiness.model.entity.UserEntity;
import space.rexhub.kbms.bussiness.model.entity.UserProjEntity;

/**
 * Description: 用户项目关联Mapper
 */
public interface UserProjMapper extends BaseMapper<UserProjEntity> {
    /**
     * 移除项目成员
     * @param projId 项目id
     * @param userId 用户id
     */
    @InterceptorIgnore(tenantLine = "on")
    void removeProjMember(@Param("projId") Long projId, @Param("userId") Long userId);
}
