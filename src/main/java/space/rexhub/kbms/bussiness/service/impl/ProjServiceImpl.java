package space.rexhub.kbms.bussiness.service.impl;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import space.rexhub.kbms.bussiness.dao.DocMapper;
import space.rexhub.kbms.bussiness.dao.FileMapper;
import space.rexhub.kbms.bussiness.dao.ProjMapper;
import space.rexhub.kbms.bussiness.dao.UserProjMapper;
import space.rexhub.kbms.bussiness.es_dao.EsDocDao;
import space.rexhub.kbms.bussiness.model.entity.DocEntity;
import space.rexhub.kbms.bussiness.model.entity.FileEntity;
import space.rexhub.kbms.bussiness.model.entity.ProjEntity;
import space.rexhub.kbms.bussiness.model.entity.UserProjEntity;
import space.rexhub.kbms.bussiness.model.form.ProjMemberForm;
import space.rexhub.kbms.bussiness.model.form.ProjForm;
import space.rexhub.kbms.bussiness.model.form.ProjMemberPageForm;
import space.rexhub.kbms.bussiness.model.form.ProjPageForm;
import space.rexhub.kbms.bussiness.model.vo.ProjMemberVO;
import space.rexhub.kbms.bussiness.model.vo.ProjVO;
import space.rexhub.kbms.bussiness.model.vo.UserDetailVO;
import space.rexhub.kbms.bussiness.service.DocService;
import space.rexhub.kbms.bussiness.service.FileService;
import space.rexhub.kbms.bussiness.service.ProjService;
import space.rexhub.kbms.bussiness.service.UserService;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.constant.Constant;
import space.rexhub.kbms.common.exception.CommonException;
import space.rexhub.kbms.common.utils.ConvertUtil;

import java.util.Random;

/**
 * Description: 项目服务实现类
 *
 */
@Service
public class ProjServiceImpl extends ServiceImpl<ProjMapper, ProjEntity> implements ProjService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProjMapper userProjMapper;


    @Autowired
    private DocService docService;

    @Autowired
    private FileService fileService;

    @Autowired
    private EsDocDao esDocDao;
    /**
     * 创建项目
     *
     * @param form 创建项目表单
     */
    @Override
    public void createProj(ProjForm form) {
        // 创建项目信息
        ProjEntity projEntity = ConvertUtil.copyProperties(form, ProjEntity.class);
        Random random = new Random();
        String imgUrl = Constant.IMG_LIST.get(random.nextInt(20) + 1);
        projEntity.setImg(imgUrl);
        projEntity.setStatus(Constant.ProjStatus.NOT_START);
        projEntity.setDeleted(Constant.SoftDelete.EXIST);
        save(projEntity);
        // 创建关联信息
        UserDetailVO currentUser = userService.getCurrentUser();
        UserProjEntity userProjEntity = new UserProjEntity();
        userProjEntity.setProjId(projEntity.getId());
        userProjEntity.setUserId(currentUser.getId());
        userProjEntity.setType(Constant.MemberType.OWNER);
        userProjMapper.insert(userProjEntity);
    }

    /**
     * 获取项目分页信息
     *
     * @param form 表单悉尼下
     * @return 分页数据
     */
    @Override
    public IPage<ProjVO> getProjPage(ProjPageForm form) {
        QueryWrapper<ProjVO> queryWrapper = new QueryWrapper<ProjVO>()
                .eq("deleted", Constant.SoftDelete.EXIST)
                .like(StringUtils.hasLength(form.getTitle()), "title", form.getTitle());
        return baseMapper.getProjPage(form.toPage(), queryWrapper);
    }

    /**
     * 获取项目详情
     *
     * @param id 项目id
     * @return 项目信息
     */
    @Override
    public ProjVO getProjInfo(Long id) {
        return baseMapper.getProjInfo(id);
    }

    /**
     * 更新项目信息
     *
     * @param form 更新表单
     */
    @Override
    public void updateProj(ProjForm form) {
        ProjEntity entity = getById(form.getId());
        if (entity == null) {
            throw new CommonException(CommonStatus.USER_NOT_EXIST);
        }
        ConvertUtil.copyPropertiesIgnoreNull(form, entity);
        updateById(entity);
    }

    /**
     * 获取项目的参会成员
     *
     * @param form 项目成员分页参数
     * @return 参会成员信息
     */
    @Override
    public IPage<ProjMemberVO> getProjMembers(ProjMemberPageForm form) {
        // 判断当前成员是否有权限
        Long userId = userService.getCurrentUser().getId();
        QueryWrapper<UserProjEntity> userProjEntityQueryWrapper = new QueryWrapper<UserProjEntity>()
                .eq("proj_id", form.getProjId());
        UserProjEntity userProjEntity = userProjMapper.selectOne(userProjEntityQueryWrapper);
        if (userProjEntity == null) {
            throw new CommonException(CommonStatus.NOT_ALLOWED);
        }
        // 获取项目成员信息
        QueryWrapper<ProjMemberVO> projMemberVOQueryWrapper = new QueryWrapper<ProjMemberVO>()
                .eq("proj_id", form.getProjId())
                .like(form.getUsername() != null, "username", form.getUsername());
        return baseMapper.getProjMembers(form.toPage(), projMemberVOQueryWrapper);
    }


    /**
     * 添加项目成员
     *
     * @param form 添加项目成员表单
     */
    @Override
    public void addProjMember(ProjMemberForm form) {
        UserDetailVO user = userService.getByUsername(form.getUsername());
        ProjEntity proj = getById(form.getProjId());
        if (user == null || proj == null) {
            throw new CommonException(CommonStatus.ID_NOT_EXISTS);
        }
        UserProjEntity userProjEntity = new UserProjEntity();
        userProjEntity.setUserId(user.getId());
        userProjEntity.setProjId(proj.getId());
        userProjEntity.setType(Constant.MemberType.PARTICIPATOR);
        userProjMapper.insert(userProjEntity);
    }

    /**
     * 移除项目成员
     *
     * @param form 移除项目成员表单
     */
    @Override
    public void removeProjMember(ProjMemberForm form) {
        // id不存在
        UserDetailVO user = userService.getByUsername(form.getUsername());
        ProjEntity proj = getById(form.getProjId());
        if (user == null || proj == null) {
            throw new CommonException(CommonStatus.ID_NOT_EXISTS);
        }
        userProjMapper.removeProjMember(form.getProjId(), user.getId());
    }

    /**
     * 获取当前登陆用户信息
     *
     * @param projId 项目id
     * @return 项目当前用户成员信息
     */
    @Override
    public ProjMemberVO getProjCurrentUserInfo(Long projId) {
        UserDetailVO currentUser = userService.getCurrentUser();

        return baseMapper.getProjMemberInfo(projId, currentUser.getId());
    }

    /**
     * 删除项目信息
     *
     * @param id 项目id
     */
    @Override
    public void removeProj(Long id) {
        // 删除项目成员[别删，删了logstash没法更新]
//        userProjMapper.delete(new QueryWrapper<UserProjEntity>().eq("proj_id", id));
        // 项目文档进行软删除
        docService.update(new UpdateWrapper<DocEntity>()
                .eq("proj_id", id)
                .set("deleted", Constant.SoftDelete.DELETED));
        // 删除项目相关的文件
        fileService.update(new UpdateWrapper<FileEntity>()
                .eq("proj_id", id)
                .set("deleted", Constant.SoftDelete.DELETED));
        // 删除项目信息
        ProjEntity projEntity = getById(id);
        projEntity.setDeleted(Constant.SoftDelete.DELETED);
        updateById(projEntity);
    }
}
