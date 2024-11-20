package space.rexhub.kbms.common.constant;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量类
 */
public class Constant {
  public static final String CAPTCHA_KEY = "captcha";
  public static final String  ADMIN_AUTHORIZATION= "Authorization";
  public static final String DEFAULT_PASSWORD = "88888888";
  public interface SortStrategy{
    String ASC = "asc";
    String DESC = "desc";
  }

  /**
   * 项目状态
   */
  public interface ProjStatus{
    // 未开始
    Byte NOT_START = 1;
    // 进行中
    Byte IN_PROGRESS = 2;
    // 已暂停
    Byte PAUSED = 3;
    // 已完成
    Byte FINISHED = 4;
    // 已取消
    Byte CANCELED = 5;
  }
  /**
   * 文档形式生成
   */
  public interface DocOrigin{
    // 文本形式生成
    Byte TEXT = 0;
    // 文件上传形式生成
    Byte UPLOAD = 1;
  }

  /**
   * 检索类型
   */
  public interface SearchType{
    // 未删除
    String MYSQL = "mysql";
    // 已删除
    String ES = "es";
  }

  /**
   * 软删除
   */
  public interface SoftDelete{
    // 未删除
    Byte EXIST = 0;
    // 已删除
    Byte DELETED = 1;
  }

  /**
   * 成员类型
   */
  public interface MemberType{
    // 所有者
    Byte OWNER = 0;
    // 参与者
    Byte PARTICIPATOR = 1;
  }
  public static final List<String> IMG_LIST = new ArrayList<>(20);
  static {
    for(int i = 1; i <= 20; i++){
      IMG_LIST.add("/static/img/proj/"+i+".jpg");
    }
  }



}
