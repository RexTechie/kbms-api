package space.rexhub.kbms.bussiness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import space.rexhub.kbms.bussiness.dao.TagMapper;
import space.rexhub.kbms.bussiness.model.dto.TagDetailDTO;
import space.rexhub.kbms.bussiness.model.entity.TagEntity;
import space.rexhub.kbms.bussiness.model.form.TagForm;
import space.rexhub.kbms.bussiness.model.form.TagPageForm;
import space.rexhub.kbms.bussiness.model.vo.TagTreeVO;
import space.rexhub.kbms.bussiness.service.TagService;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.exception.CommonException;
import space.rexhub.kbms.common.utils.ConvertUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 标签服务实现类
 *
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity> implements TagService {

    /**
     * 新增标签
     *
     * @param form 标签表单
     */
    @Override
    public void insertTag(TagForm form) {
        TagEntity tagEntity = ConvertUtil.copyProperties(form, TagEntity.class);
        // 若标签名称已存在，报错
        if (getOne(new QueryWrapper<TagEntity>()
                .eq("name", form.getName())) != null){
            throw new CommonException(CommonStatus.NAME_EXISTS);
        }
        save(tagEntity);
    }

    /**
     * 更新标签
     *
     * @param form 标签表单
     */
    @Override
    public void updateTag(TagForm form) {
        TagEntity oldEntity = getById(form.getId());
        if (oldEntity == null){
            throw new CommonException(CommonStatus.ID_NOT_EXISTS);
        }
        // 修改的名称和原来不一样并且已存在，则报错
        if (!oldEntity.getName().equals(form.getName()) &&
            getOne(new QueryWrapper<TagEntity>().eq("name", form.getName())) != null
            ){
            throw new CommonException(CommonStatus.NAME_EXISTS);
        }
        ConvertUtil.copyPropertiesIgnoreNull(form, oldEntity);
        updateById(oldEntity);
    }

    /**
     * 获取标签分页数据
     *
     * @param form 标签分页表单
     * @return 标签分页信息
     */
    @Override
    public List<TagTreeVO> getTagTree(TagPageForm form) {
        List<TagEntity> tagPointList = baseMapper.getTagList(form.getName());
        // 找出各级标签集合
        List<TagEntity> level1 = tagPointList.stream()
                .filter(k -> k.getLevel() == 1).collect(Collectors.toList());
        List<TagEntity> level2 = tagPointList.stream()
                .filter(k -> k.getLevel() == 2).collect(Collectors.toList());
        List<TagEntity> level3 = tagPointList.stream()
                .filter(k -> k.getLevel() == 3).collect(Collectors.toList());
        // 构造标签树
        List<TagTreeVO> tagTreeVOList = new ArrayList<>();
        // 一级标签
        level1.forEach(k1 -> {
            // 一级标签的孩子
            List<TagTreeVO> k1Children = new ArrayList<>();
            TagTreeVO k1Tree = ConvertUtil.copyProperties(k1, TagTreeVO.class);
            //二级标签
            level2.forEach(k2 -> {
                // 标记k1有子级
                if (k2.getParentId().equals(k1.getId())){
                    List<TagTreeVO> k2Children = new ArrayList<>();
                    TagTreeVO k2Tree = ConvertUtil.copyProperties(k2, TagTreeVO.class);
                    // 三级标签
                    level3.forEach(k3 -> {
                        // 标记k2有子级
                        if (k3.getParentId().equals(k2.getId())){
                            // 标记k3无子级
                            TagTreeVO k3Tree = ConvertUtil.copyProperties(k3, TagTreeVO.class);
                            k2Children.add(k3Tree);
                        }
                    });
                    if (k2Children.size() > 0) {
                        k2Tree.setChildren(k2Children);
                    }
                    k1Children.add(k2Tree);
                }
            });
            if (k1Children.size() > 0) {
                k1Tree.setChildren(k1Children);
            }
            tagTreeVOList.add(k1Tree);
        });
        return tagTreeVOList;
    }

    /**
     * 根据标签id删除标签
     *
     * @param id 标签
     */
    @Override
    public void deleteTag(Long id) {
        // 有子级不能删
        int childCnt = count(new QueryWrapper<TagEntity>().eq("parent_id", id));
        // 项目已经用到该标签不能删除
//        int cnt = questionService.count(new QueryWrapper<QuestionEntity>().eq("tag_id", id));
        if (childCnt > 0
//                || cnt > 0
        ) {
            throw new CommonException(CommonStatus.CANNOT_DELETE);
        }
        removeById(id);
    }
}
