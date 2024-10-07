package space.rexhub.kbms.bussiness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rexhub.kbms.bussiness.dao.FileMapper;
import space.rexhub.kbms.bussiness.dao.UserProjMapper;
import space.rexhub.kbms.bussiness.model.entity.FileEntity;
import space.rexhub.kbms.bussiness.model.entity.UserProjEntity;
import space.rexhub.kbms.bussiness.model.form.ProjFilePageForm;
import space.rexhub.kbms.bussiness.model.vo.ProjFileVO;
import space.rexhub.kbms.bussiness.model.vo.ProjMemberVO;
import space.rexhub.kbms.bussiness.service.FileService;
import space.rexhub.kbms.bussiness.service.UserService;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.constant.Constant;
import space.rexhub.kbms.common.exception.CommonException;

/**
 * Description: 文件服务实现类
 *
 * @author Rex
 * @date 2024-03-22
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserProjMapper userProjMapper;

    private void verifyPermissions(Long projId){
        // 判断当前成员是否有权限
        Long userId = userService.getCurrentUser().getId();
        QueryWrapper<UserProjEntity> userProjEntityQueryWrapper = new QueryWrapper<UserProjEntity>()
                .eq("proj_id", projId);
        UserProjEntity userProjEntity = userProjMapper.selectOne(userProjEntityQueryWrapper);
        if (userProjEntity == null) {
            throw new CommonException(CommonStatus.NOT_ALLOWED);
        }
    }
    /**
     * 获取项目文件分页信息
     *
     * @param form 分页表单
     * @return 分页信息
     */
    @Override
    public IPage<ProjFileVO> getFilePage(ProjFilePageForm form) {
        verifyPermissions(form.getProjId());
        // 获取项目成员信息
        QueryWrapper<ProjFileVO> projFileVOQueryWrapper = new QueryWrapper<ProjFileVO>()
                .eq("proj_id", form.getProjId())
                .eq("deleted", Constant.SoftDelete.EXIST)
                .like(form.getFileName() != null, "file_name", form.getFileName());
        return baseMapper.getProjMembers(form.toPage(), projFileVOQueryWrapper);
    }

    /**
     * 获取文件信息
     *
     * @param fileId 文件id
     * @param projId 关联项目id
     * @return 文件信息
     */
    @Override
    public FileEntity getFileInfo(Long fileId, Long projId) {
//        verifyPermissions(projId);
        return getById(fileId);
    }

    /**
     * 保存文件信息至数据库
     *
     * @param projFile 项目文件信息
     */
    @Override
    public void insertFile(FileEntity projFile) {
        verifyPermissions(projFile.getProjId());
        save(projFile);
    }

    /**
     * 删除文件
     *
     * @param fileId 文件id
     * @param projId 项目id
     */
    @Override
    public void deleteFile(Long fileId, Long projId) {
        verifyPermissions(projId);
        FileEntity fileEntity = getById(fileId);
        fileEntity.setDeleted(Constant.SoftDelete.DELETED);
        updateById(fileEntity);
    }
}
