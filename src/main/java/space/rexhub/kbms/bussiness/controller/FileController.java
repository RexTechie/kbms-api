package space.rexhub.kbms.bussiness.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.IoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import lombok.extern.log4j.Log4j2;
import org.jodconverter.DocumentConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.document.DocumentFormat;
import org.jodconverter.document.DocumentFormatRegistry;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.rexhub.kbms.bussiness.model.entity.FileEntity;
import space.rexhub.kbms.bussiness.model.form.ProjFilePageForm;
import space.rexhub.kbms.bussiness.model.vo.ProjFileVO;
import space.rexhub.kbms.bussiness.service.FileService;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.constant.Constant;
import space.rexhub.kbms.common.exception.CommonException;
import space.rexhub.kbms.common.model.vo.Result;
import sun.nio.ch.IOUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.UUID;

/**
 * Description: 项目文件api
 *
 * @author Rex
 * @date 2024-03-22
 */
@RestController
@RequestMapping("/proj")
@Log4j2
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private DocumentConverter documentConverter;

    @Value("${proj.file-dir}")
    private String baseDir;

    @GetMapping("/files")
    public Result<IPage<ProjFileVO>> getFilePage(@Validated ProjFilePageForm form){
        return Result.success(fileService.getFilePage(form));
    }

    /**
     * 文件上传
     * @param file 文件
     * @param projId 项目id
     * @return 上传结果
     */
    @PostMapping("/file/upload")
    public Result<Void> uploadFile(@RequestPart("file") MultipartFile file,
                                   Long projId) {
        // 获取文件名称
        String fileName = file.getOriginalFilename();
        log.info("fileName: " + fileName);
//        QueryWrapper<FileEntity> fileNameTestQueryWrapper = new QueryWrapper<FileEntity>().eq("file_name", fileName);
//        if (!ObjectUtils.isEmpty(fileService.getOne(fileNameTestQueryWrapper))) {
//            return Result.error(CommonStatus.UPLOAD_ERROR.getCode(), "存在同名文件");
//        }
        // 获取文件的后缀名
        String suffix = null;
        try {
            suffix = fileName.substring(fileName.lastIndexOf('.'));
        } catch (Exception e) {
            suffix = "";
        }
        // 文件路径
        String filePath = "/" + projId + "/" + fileName;
        String absoluteFilePath = baseDir + filePath;
        log.info("projFileDir: " + filePath);
        // 构造实体类
        FileEntity projFile = new FileEntity();
        projFile.setProjId(projId);
        projFile.setFileName(fileName);
        projFile.setFilePath(filePath);
        projFile.setSuffix(suffix);
        projFile.setDeleted(Constant.SoftDelete.EXIST);
        // 保存文件
        try {
            FileUtil.writeBytes(file.getBytes(), absoluteFilePath);
            fileService.insertFile(projFile);
            return Result.success();
        } catch (IOException e) {
            return Result.error(CommonStatus.UPLOAD_ERROR);
        }
    }

    /**
     * 文件下载
     * @param fileId 文件id
     * @param projId 项目id
     * @param resp 响应体
     * @throws UnsupportedEncodingException 异常
     */
    @GetMapping("/file/download/{fileId}")
    public void downloadFile(@PathVariable("fileId") Long fileId,
                                     @RequestParam("projid") Long projId,
                                     HttpServletResponse resp) throws UnsupportedEncodingException {
        FileEntity file = fileService.getFileInfo(fileId, projId);
        String absoluteFilePath = baseDir + file.getFilePath();
        String fileName = file.getFileName();
        //清空输出流
        resp.reset();
        resp.setHeader("Content-Type", "application/octet-stream;");
        resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        try(ServletOutputStream os = resp.getOutputStream();
            FileInputStream fis = new FileInputStream(absoluteFilePath);
            // 使用缓冲流加速文件下载传输
            BufferedOutputStream bos = new BufferedOutputStream(os);
            BufferedInputStream bis = new BufferedInputStream(fis)) {
            IoUtil.copy(fis, os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件预览
     * @param fileId 文件id
     * @param projId 项目id
     * @param response 响应体
     */
    @GetMapping("/file/preview/{fileId}")
    public void toPdfFile(@PathVariable("fileId") Long fileId,
                          @RequestParam("projid") Long projId,
                          HttpServletResponse response) {
        // 获取文件信息
        FileEntity projFile = fileService.getFileInfo(fileId, projId);
        // 获取文件名
        String fileName = projFile.getFileName();
        // 获取文件绝对路径
        String absoluteFilePath = baseDir + projFile.getFilePath();
        // 获取本地文件
        File file = new File(absoluteFilePath);
        try(// 使用response,将pdf文件以流的方式发送的前端浏览器上
            ServletOutputStream out = response.getOutputStream();
            // 读取文件
            InputStream in = FileUtil.getInputStream(file);
            ){
            // 清空response
            response.reset();
            response.setCharacterEncoding("utf-8");
            // 获取文件类型
            String fileType = FileTypeUtil.getType(file);
            if (StrUtil.endWithAnyIgnoreCase(fileName, ".doc", ".docx", ".xls", ".xlsx", ".csv", ".ppt", ".pptx")) {
                response.setContentType(MediaType.APPLICATION_PDF_VALUE);
                // 转为PDF
                documentConverter.convert(in)
                        .as(DefaultDocumentFormatRegistry.getFormatByExtension(fileType))
                        .to(out)
                        .as(DefaultDocumentFormatRegistry.getFormatByExtension("pdf"))
                        .execute();
            } else if (StrUtil.endWithAnyIgnoreCase(fileName,
                    ".pdf", ".txt", ".xml", ".md", ".json",
                    ".html", ".htm", ".gif", ".jpg",
                    ".jpeg", ".png", ".ico", ".bmp")) {
                // 动态配置content-type
                Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(fileName);
                mediaType.ifPresent(type -> {
                    response.setContentType(type.toString());
                });
                IoUtil.copy(in, out);
            } else {
                out.write("暂不支持预览此类型附件".getBytes());
            }
        } catch (Exception e) {
            throw new CommonException(CommonStatus.SERVER_ERROR);
        }
    }

    /**
     * 移除文件
     * @param fileId 文件id
     * @param projId 项目id
     * @return 文件信息
     */
    @DeleteMapping("/file/{fileId}")
    public Result<Void> deleteFile(@PathVariable("fileId") Long fileId,
                          @RequestParam("projid") Long projId) {
        // 获取文件信息
        FileEntity fileInfo = fileService.getFileInfo(fileId, projId);
        if (fileInfo != null) {
            // 移除数据库
            fileService.deleteFile(fileId, projId);
            // 移除本地文件
            FileUtil.del(baseDir + fileInfo.getFilePath());
        }
        return Result.success();
    }
}
