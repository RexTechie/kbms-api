package space.rexhub.kbms.bussiness.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HtmlUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.log4j.Log4j2;
import org.jodconverter.DocumentConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.rexhub.kbms.bussiness.model.entity.DocEntity;
import space.rexhub.kbms.bussiness.model.form.DocForm;
import space.rexhub.kbms.bussiness.model.form.DocPageForm;
import space.rexhub.kbms.bussiness.model.form.FullTextSearchForm;
import space.rexhub.kbms.bussiness.model.vo.DocDetailVO;
import space.rexhub.kbms.bussiness.model.vo.DocVO;
import space.rexhub.kbms.bussiness.model.vo.DocSearchPageVO;
import space.rexhub.kbms.bussiness.service.DocService;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.constant.Constant;
import space.rexhub.kbms.common.exception.CommonException;
import space.rexhub.kbms.common.model.vo.Result;
import space.rexhub.kbms.common.utils.ConvertUtil;
import space.rexhub.kbms.common.validation.Add;
import space.rexhub.kbms.common.validation.Update;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Description: 项目文档api
 *
 */
@RestController
@RequestMapping("/proj")
@Log4j2
public class DocController {

    @Autowired
    private DocService docService;

    @Autowired
    private DocumentConverter documentConverter;

    /**
     * 创建文档
     * @param form 创建文档表单
     * @return 创建结果
     */
    @PostMapping("/doc")
    public Result<Void> createDoc(@RequestBody @Validated(Add.class) DocForm form){
        DocEntity docEntity = ConvertUtil.copyProperties(form, DocEntity.class);
        docEntity.setIsUpload(Constant.DocOrigin.TEXT);
        docEntity.setDeleted(Constant.SoftDelete.EXIST);
        docService.save(docEntity);
        return Result.success();
    }

    /**
     * 更新文档
     * @param form 更新文档表单
     * @return 更新结果
     */
    @PutMapping("/doc")
    public Result<Void> updateDoc(@RequestBody @Validated(Update.class) DocForm form){
        DocEntity target = docService.getById(form.getId());
        if (target == null){
            throw new CommonException(CommonStatus.ID_NOT_EXISTS);
        }
        ConvertUtil.copyPropertiesIgnoreNull(form, target);
        docService.updateById(target);
        return Result.success();
    }

    /**
     * 获取文档分页数据
     * @param form
     * @return 文档分页数据
     */
    @GetMapping("/docs")
    public Result<IPage<DocVO>> getDocPage(@Validated DocPageForm form){
        return Result.success(docService.getDocPage(form));
    }

    /**
     * 根据id获取文档详情
     * @param id 文档id
     * @return 获取文档详情
     */
    @GetMapping("/doc/{id}")
    public Result<DocDetailVO> getDocInfo(@PathVariable(value = "id") Long id){
        return Result.success(docService.getDocDetailVoPage(id));
    }

    /**
     * 根据id删除文档详情
     * @param docId 文档id
     * @return 获取文档详情
     */
    @DeleteMapping("/doc/{id}")
    public Result<Void> deleteDoc(@PathVariable(value = "id") Long docId, @RequestParam("projid") Long projId){
        docService.deleteDoc(docId, projId);
        return Result.success();
    }

    /**
     * 文件上传
     * @param file 文件
     * @param projId 项目id
     * @return 上传结果
     */
    @PostMapping("/doc/upload")
    public Result<Void> uploadFile(@RequestPart("file") MultipartFile file,
                                   Long projId) throws IOException {
        // 获取文件名称
        String fileName = file.getOriginalFilename();
        log.info("fileName: " + fileName);
        // 获取文件的后缀名
        String suffix = FileUtil.getSuffix(fileName);
        String name = FileUtil.getName(fileName);
        DocEntity docEntity = new DocEntity();
        try (
                InputStream is = file.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ){
            // 将word转为html
            documentConverter.convert(is)
                    .as(DefaultDocumentFormatRegistry.getFormatByExtension(suffix))
                    .to(baos).
                    as(DefaultDocumentFormatRegistry.HTML)
                    .execute();
            String result = baos.toString(StandardCharsets.UTF_8.name());
            // 移除无关标签
            result = HtmlUtil.removeHtmlTag(result, "style", "head");
            result = HtmlUtil.unwrapHtmlTag(result, "html", "!DOCTYPE html", "body", "center", "font");
            // 转为文档实体类
            docEntity.setTitle(name);
            docEntity.setSummary(name);
            docEntity.setProjId(projId);
            docEntity.setContentHtml(result);
            result = HtmlUtil.cleanHtmlTag(result);
            docEntity.setContentText(result);
            docEntity.setIsUpload(Constant.DocOrigin.UPLOAD);
            docEntity.setDeleted(Constant.SoftDelete.EXIST);
            docService.save(docEntity);
            System.out.println(result);
            return Result.success();
        }catch (Exception e) {
            throw new CommonException(CommonStatus.UPLOAD_ERROR.getCode(), e.getMessage());
        }
    }


    /**
     * 全文检索
     * @param form
     * @return
     */
    @GetMapping("/doc/search")
    public Result<DocSearchPageVO> search(@Validated FullTextSearchForm form) {
        if (Constant.SearchType.MYSQL.equalsIgnoreCase(form.getType())){
            return Result.success(docService.searchByMySql(form));
        }else {
            return Result.success(docService.searchByEs(form));
        }
    }

}
