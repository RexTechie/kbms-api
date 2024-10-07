package space.rexhub.kbms.bussiness.dao;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import space.rexhub.kbms.bussiness.model.entity.ProjEntity;
import space.rexhub.kbms.bussiness.model.vo.ProjMemberVO;
import space.rexhub.kbms.bussiness.model.vo.ProjVO;

/**
 * Description: 项目Mapper
 *
 * @author Rex
 * @date 2024-03-18
 */
public interface ProjMapper extends BaseMapper<ProjEntity> {
    /**
     * 根据项目分页数据
     * @param page 分页参数
     * @param queryWrapper 分页表单
     * @return 练习分页数据
     */
    IPage<ProjVO> getProjPage(IPage<ProjVO> page,
                              @Param("ew") QueryWrapper<ProjVO> queryWrapper);

    /**
     * 获取项目信息
     * @param id 项目id
     * @return 项目信息
     */
    ProjVO getProjInfo(Long id);

    /**
     * 获取项目成员分页数据
     * @param page 分页参数
     * @param queryWrapper 分页表单
     * @return 项目成员分页数据
     */
    @InterceptorIgnore(tenantLine = "on")
    IPage<ProjMemberVO> getProjMembers(IPage<ProjMemberVO> page,
                                       @Param("ew") QueryWrapper<ProjMemberVO> queryWrapper);

    /**
     * 获取项目成员详情信息
     * @param projId 项目id
     * @param userId 用户id
     * @return 项目成员详情信息
     */
    @InterceptorIgnore(tenantLine = "on")
    ProjMemberVO getProjMemberInfo(@Param("projId") Long projId, @Param("userId") Long userId);

}
