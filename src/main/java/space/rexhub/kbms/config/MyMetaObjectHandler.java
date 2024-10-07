package space.rexhub.kbms.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import space.rexhub.kbms.bussiness.model.vo.UserDetailVO;
import space.rexhub.kbms.bussiness.service.UserService;

import java.util.Date;

/**
 * 自定义实现类 MyMetaObjectHandler
 * 自动更新create_time、update_time、creator、updater字段
 * @author Rex
 * @date 2021-05-11 9:09
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

  @Autowired
  @Lazy
  private UserService userService;
  /**
   * 插入时候的填充策略
   */
  @Override
  public void insertFill(MetaObject metaObject) {
    log.info("start insert fill ....");
    this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
    this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    UserDetailVO user = userService.getCurrentUser();
    long userId = 0L;
    if (user != null){
      userId = user.getId();
    }
    this.strictInsertFill(metaObject, "creator", Long.class, userId);
    this.strictInsertFill(metaObject, "updater", Long.class, userId);

  }

  /**
   * 更新时间的填充策略
   */
  @Override
  public void updateFill(MetaObject metaObject) {
    log.info("start update fill ....");
     // strictUpdateFill只会填充字段为空的值
    boolean hasUpdateTime = metaObject.hasSetter("updateTime");
    if (hasUpdateTime) {
      metaObject.setValue("updateTime", null);
      this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
    boolean hasUpdater = metaObject.hasSetter("updater");
    if (hasUpdater) {
      UserDetailVO user = userService.getCurrentUser();
      long userId = 0L;
      if (user != null){
        userId = user.getId();
      }
      metaObject.setValue("updater", null);
      this.strictUpdateFill(metaObject, "updater" ,Long.class, userId);
    }
  }

}
