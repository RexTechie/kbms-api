package space.rexhub.kbms.bussiness.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import space.rexhub.kbms.bussiness.model.entity.DocEntity;
import space.rexhub.kbms.bussiness.model.form.DocPageForm;
import space.rexhub.kbms.bussiness.model.form.FullTextSearchForm;
import space.rexhub.kbms.bussiness.model.vo.DocDetailVO;
import space.rexhub.kbms.bussiness.model.vo.DocVO;
import space.rexhub.kbms.bussiness.model.vo.DocSearchPageVO;

/**
 * Description: 文档服务类
 *
 */
public interface DocService  extends IService<DocEntity> {
    /**
     * 获取文档分页数据
     * @param form 文档分页表单
     * @return 分页数据结果
     */
    IPage<DocVO> getDocPage(DocPageForm form);

    /**
     * 根据id获取文档
     * @param id 文档id
     * @return 文档信息
     */
    DocDetailVO getDocDetailVoPage(Long id);

    /**
     * 删除文档
     * @param docId 文档id
     * @param projId 项目id
     */
    void deleteDoc(Long docId, Long projId);

    /**
     * 通过Elasticsearch全文检索
     * @param form 全文检索表单
     * @return 检索得到的文档
     */
    DocSearchPageVO searchByEs(FullTextSearchForm form);

    /**
     * 通过MySql全文检索
     * @param form 全文检索表单
     * @return 检索得到的文档
     */
    DocSearchPageVO searchByMySql(FullTextSearchForm form);
}
