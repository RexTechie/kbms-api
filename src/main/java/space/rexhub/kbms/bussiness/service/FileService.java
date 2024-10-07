package space.rexhub.kbms.bussiness.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import space.rexhub.kbms.bussiness.model.entity.FileEntity;
import space.rexhub.kbms.bussiness.model.form.ProjFilePageForm;
import space.rexhub.kbms.bussiness.model.vo.ProjFileVO;

/**
 * Description: 文件服务类
 *
 * @author Rex
 * @date 2024-03-22
 */
public interface FileService extends IService<FileEntity> {

    /**
     * 获取项目文件分页信息
     * @param form 分页表单
     * @return 分页信息
     */
    IPage<ProjFileVO> getFilePage(ProjFilePageForm form);

    /**
     * 获取文件信息
     * @param fileId 文件id
     * @param projId 关联项目id
     * @return 文件信息
     */
    FileEntity getFileInfo(Long fileId, Long projId);

    /**
     * 保存文件信息至数据库
     * @param projFile 项目文件信息
     */
    void insertFile(FileEntity projFile);

    /**
     * 删除文件
     * @param fileId 文件id
     * @param projId 项目id
     */
    void deleteFile(Long fileId, Long projId);
}
