package space.rexhub.kbms.bussiness.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import space.rexhub.kbms.bussiness.model.entity.ProjEntity;
import space.rexhub.kbms.bussiness.model.form.ProjMemberForm;
import space.rexhub.kbms.bussiness.model.form.ProjForm;
import space.rexhub.kbms.bussiness.model.form.ProjMemberPageForm;
import space.rexhub.kbms.bussiness.model.form.ProjPageForm;
import space.rexhub.kbms.bussiness.model.vo.ProjMemberVO;
import space.rexhub.kbms.bussiness.model.vo.ProjVO;

/**
 * Description: 项目服务类
 *
 */
public interface ProjService extends IService<ProjEntity> {
    /**
     * 创建项目
     * @param form 创建项目表单
     */
    void createProj(ProjForm form);

    /**
     * 获取项目分页信息
     * @param form
     * @return
     */
    IPage<ProjVO> getProjPage(ProjPageForm form);

    /**
     * 获取项目详情
     * @param id 项目id
     * @return 项目信息
     */
    ProjVO getProjInfo(Long id);

    /**
     * 更新项目信息
     * @param form 更新表单
     */
    void updateProj(ProjForm form);

    /**
     * 获取项目的参会成员
     * @param form 项目成员分页参数
     * @return 参会成员信息
     */
    IPage<ProjMemberVO> getProjMembers(ProjMemberPageForm form);

    /**
     * 添加项目成员
     * @param form 添加项目成员表单
     */
    void addProjMember(ProjMemberForm form);

    /**
     * 移除项目成员
     * @param form 移除项目成员表单
     */
    void removeProjMember(ProjMemberForm form);

    /**
     * 获取当前登陆用户信息
     * @param projId 项目id
     * @return 项目当前用户成员信息
     */
    ProjMemberVO getProjCurrentUserInfo(Long projId);

    /**
     * 删除项目信息
     * @param id 项目id
     */
    void removeProj(Long id);
}
