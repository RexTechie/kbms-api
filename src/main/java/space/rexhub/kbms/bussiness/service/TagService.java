package space.rexhub.kbms.bussiness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import space.rexhub.kbms.bussiness.model.entity.TagEntity;
import space.rexhub.kbms.bussiness.model.form.TagForm;
import space.rexhub.kbms.bussiness.model.form.TagPageForm;
import space.rexhub.kbms.bussiness.model.vo.TagTreeVO;

import java.util.List;

/**
 * Description: 知识点
 *
 */
public interface TagService extends IService<TagEntity> {

    /**
     * 新增知识点
     * @param form 知识点表单
     */
    void insertTag(TagForm form);

    /**
     * 更新知识点
     * @param form 知识点表单
     */
    void updateTag(TagForm form);

    /**
     * 获取知识点分页数据
     * @param form 知识点分页表单
     * @return 知识点分页信息
     */
    List<TagTreeVO> getTagTree(TagPageForm form);

    /**
     * 根据知识点id删除知识点
     * @param id 知识点
     */
    void deleteTag(Long id);
}
