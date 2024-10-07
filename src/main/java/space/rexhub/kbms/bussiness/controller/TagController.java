package space.rexhub.kbms.bussiness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.rexhub.kbms.bussiness.model.entity.TagEntity;
import space.rexhub.kbms.bussiness.model.form.TagForm;
import space.rexhub.kbms.bussiness.model.form.TagPageForm;
import space.rexhub.kbms.bussiness.model.vo.TagTreeVO;
import space.rexhub.kbms.bussiness.service.TagService;
import space.rexhub.kbms.common.model.vo.Result;
import space.rexhub.kbms.common.validation.Add;
import space.rexhub.kbms.common.validation.Update;

import java.util.List;

/**
 * Description: 标签api
 *
 * @author Rex
 * @date 2023-07-04
 */
@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 根据id获取标签信息
     * @param id id
     * @return 标签信息
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/tag/{id}")
    public Result<TagEntity> getTagById(@PathVariable("id") String id) {
        return Result.success(tagService.getById(id));
    }

    /**
     * 获取标签列表
     * @return 标签列表
     */
    @GetMapping("/tags")
    public Result<List<TagTreeVO>> getTagPage(@Validated TagPageForm form){
        return Result.success(tagService.getTagTree(form));
    }

    /**
     * 新增标签
     * @param form 标签表单
     * @return 新增结果
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/tag")
    public Result<Void> insertTag(@RequestBody @Validated(Add.class) TagForm form){
        tagService.insertTag(form);
        return Result.success();
    }

    /**
     * 修改标签
     * @param form 标签表单
     * @return 新增结果
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/tag")
    public Result<Void> updateKnowledgePoint(@RequestBody @Validated(Update.class) TagForm form){
        tagService.updateTag(form);
        return Result.success();
    }

    /**
     * 删除标签
     * @param id 标签id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/tag/{id}")
    public Result<Void> deleteTag(@PathVariable Long id){
        tagService.deleteTag(id);
        return Result.success();
    }
}
