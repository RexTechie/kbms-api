package space.rexhub.kbms.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import space.rexhub.kbms.security.AdminUser;
import space.rexhub.kbms.security.UserDetailServiceImpl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * MybatisPlus配置
 */
@Configuration
@MapperScan(value = {"space.rexhub.kbms.bussiness.dao"})
public class MybatisPlusConfig {

  @Autowired
  private UserDetailServiceImpl userDetailService;

  private final static Set<String> TENANT_TABLES = Stream.of("tb_user_proj")
          .collect(Collectors.toSet());
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    /**
     * 新的分页插件
     * 一缓和二缓遵循mybatis的规则
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    /**
     * 多租户插件
     */
    interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
      @Override
      public Expression getTenantId() {
        // 获取用户id
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        AdminUser user = userDetailService.loadUserByUsername(auth.getName());
        return new LongValue(user.getId());
      }

      /**
       * 配置租户名称
       */
      @Override
      public String getTenantIdColumn() {
        return "user_id";
      }

      @Override
      public boolean ignoreTable(String tableName) {
        return !TENANT_TABLES.contains(tableName);
      }
    }));
    return interceptor;
  }
}

