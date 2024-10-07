package space.rexhub.kbms.bussiness.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.rexhub.kbms.bussiness.model.form.ProjMemberForm;
import space.rexhub.kbms.bussiness.model.form.ProjForm;
import space.rexhub.kbms.bussiness.model.form.ProjMemberPageForm;
import space.rexhub.kbms.bussiness.model.form.ProjPageForm;
import space.rexhub.kbms.bussiness.model.vo.ProjMemberVO;
import space.rexhub.kbms.bussiness.model.vo.ProjVO;
import space.rexhub.kbms.bussiness.service.ProjService;
import space.rexhub.kbms.common.model.vo.Result;
import space.rexhub.kbms.common.validation.Add;
import space.rexhub.kbms.common.validation.Update;

/**
 * Description: 项目api
 *
 * @author Rex
 * @date 2024-03-18
 */
@RestController
public class ProjController {
    @Autowired
    private ProjService projService;

    /**
     * 创建项目
     * @param form
     * @return
     */
    @PostMapping("/proj")
    public Result<Void> createProj(@RequestBody @Validated(Add.class) ProjForm form){
        projService.createProj(form);
        return Result.success();
    }

    /**
     * 更新项目信息
     * @param form
     * @return
     */
    @PutMapping("/proj")
    public Result<Void> updateProj(@RequestBody @Validated(Update.class) ProjForm form){
        projService.updateProj(form);
        return Result.success();
    }

    /**
     * 删除项目信息
     * @param id 项目id
     * @return 处理结果
     */
    @DeleteMapping("/proj/{id}")
    public Result<Void> removeProj(@PathVariable("id") Long id){
        projService.removeProj(id);
        return Result.success();
    }

    /**
     * 获取项目分页信息
     * @return 用户信息
     */
    @GetMapping("/projs")
    public Result<IPage<ProjVO>> getProjPage(@Validated ProjPageForm form){
        return Result.success(projService.getProjPage(form));
    }

    /**
     * 获取项目信息
     * @return 用户信息
     */
    @GetMapping("/proj/{id}")
    public Result<ProjVO> getProjInfo(@PathVariable("id") Long id){
        return Result.success(projService.getProjInfo(id));
    }

    /**
     * 获取项目成员信息
     * @param form 项目表单
     * @return 项目成员
     */
    @GetMapping("/proj/members")
    public Result<IPage<ProjMemberVO>> getProjMembers(@Validated ProjMemberPageForm form){
        return Result.success(projService.getProjMembers(form));
    }

    /**
     * 获取当前登陆用户项目成员信息
     * @param projId 项目id
     * @return 项目成员
     */
    @GetMapping("/proj/current/{projId}")
    public Result<ProjMemberVO> getProjCurrentUsrInfo(@PathVariable("projId") Long projId){
        return Result.success(projService.getProjCurrentUserInfo(projId));
    }

    /**
     * 添加项目成员
     * @param form 添加项目成员表单
     * @return 操作结果
     */
    @PostMapping("/proj/member")
    public Result<Void> addProjMember(@RequestBody @Validated ProjMemberForm form){
        projService.addProjMember(form);
        return Result.success();
    }

    /**
     * 移除项目成员
     * @param form 添加项目成员表单
     * @return 操作结果
     */
    @DeleteMapping("/proj/member")
    public Result<Void> removeProjMember(@RequestBody @Validated ProjMemberForm form){
        projService.removeProjMember(form);
        return Result.success();
    }
}
