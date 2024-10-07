package space.rexhub.kbms.bussiness.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import space.rexhub.kbms.bussiness.model.dto.TagDetailDTO;
import space.rexhub.kbms.bussiness.model.entity.TagEntity;

import java.util.List;


/**
 * Description: 知识点dao层
 * @author Rex
 */
public interface TagMapper extends BaseMapper<TagEntity> {


    /**
     * 知识点分页查询
     * @param name 知识点名称模糊查询
     * @return 知识点分页数据
     */
    List<TagEntity> getTagList(@Param("name") String name);
}




