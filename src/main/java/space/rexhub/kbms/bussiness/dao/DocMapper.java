package space.rexhub.kbms.bussiness.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import space.rexhub.kbms.bussiness.model.entity.DocEntity;
import space.rexhub.kbms.bussiness.model.vo.DocDetailVO;
import space.rexhub.kbms.bussiness.model.vo.DocSearchVO;
import space.rexhub.kbms.bussiness.model.vo.DocVO;

import java.util.List;

/**
 * Description: 项目文档mapper
 */
public interface DocMapper extends BaseMapper<DocEntity> {
    /**
     * 获取项目文档分页数据
     * @param page 分页参数
     * @param queryWrapper 分页表单
     * @return 项目文档分页数据
     */
    IPage<DocVO> getDocPage(IPage<DocVO> page,
                            @Param("ew") QueryWrapper<DocVO> queryWrapper);

    /**
     * 根据id获取文档信息
     * @param id 文档id
     * @return 文档信息
     */
    DocDetailVO getDocDetailVoPage(@Param("id") Long id);

    /**
     * 全文检索
     * @param pageInfo 分页参数
     * @param queryWrapper 查询条件
     * @return 检索结果
     */
    List<DocSearchVO> search(IPage<DocSearchVO> page,
                             @Param("ew")  QueryWrapper<DocSearchVO> queryWrapper);
}
