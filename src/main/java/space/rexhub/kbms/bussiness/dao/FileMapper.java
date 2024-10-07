package space.rexhub.kbms.bussiness.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import space.rexhub.kbms.bussiness.model.entity.FileEntity;
import space.rexhub.kbms.bussiness.model.vo.DocVO;
import space.rexhub.kbms.bussiness.model.vo.ProjFileVO;

/**
 * Description: 项目文件dao
 *
 * @author Rex
 * @date 2024-04-12
 */
public interface FileMapper extends BaseMapper<FileEntity> {

    IPage<ProjFileVO> getProjMembers(IPage<ProjFileVO> page,
                                     @Param("ew") QueryWrapper<ProjFileVO> queryWrapper);
}
