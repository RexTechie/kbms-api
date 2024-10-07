package space.rexhub.kbms.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;


/**
 * Description: 图片上传配置
 *
 * @author Rex
 * @date 2022-04-11 11:14
 */
@Configuration
public class FileUploadConfig {
  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    // 单个数据大小 KB,MB 大写
    factory.setMaxFileSize(DataSize.parse("10MB"));
    // 总上传数据大小
    factory.setMaxRequestSize(DataSize.parse("50MB"));
    return factory.createMultipartConfig();
  }
}