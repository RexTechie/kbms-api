package space.rexhub.kbms.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * 转换工具
 */
public class ConvertUtil {
  private static final Logger logger = LoggerFactory.getLogger(ConvertUtil.class);

  /**
   * 获取内容为空的属性名
   * @param source 对象
   * @return 获取内容为空的属性名
   */
  public static String[] getNullPropertyNames (Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>();
    for(PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) {
        emptyNames.add(pd.getName());
      }
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  /**
   * 复制非空属性
   * @param source 原对象
   * @param target 目标对象
   */
  public static void copyPropertiesIgnoreNull(Object source, Object target) {
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }
  public static <T> T copyProperties(Object source, Class<T> target){
    if(source == null){
      return null;
    }
    T targetObject = null;
    try {
      targetObject = target.newInstance();
      BeanUtils.copyProperties(source, targetObject);
    } catch (Exception e) {
      logger.error("convert error ", e);
    }

    return targetObject;
  }

  public static <T> List<T> copyProperties(Collection<?> sourceList, Class<T> target){
    if(sourceList == null){
      return null;
    }

    List<T> targetList = new ArrayList<>(sourceList.size());
    try {
      for(Object source : sourceList){
        T targetObject = target.newInstance();
        BeanUtils.copyProperties(source, targetObject);
        targetList.add(targetObject);
      }
    }catch (Exception e){
      logger.error("convert error ", e);
    }
    return targetList;
  }

}
