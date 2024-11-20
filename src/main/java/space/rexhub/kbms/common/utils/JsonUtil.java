package space.rexhub.kbms.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Description: json工具类
 *
 */
public class JsonUtil {
  private static final Gson GSON;

  static {
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    GSON = builder.create();
  }

  private JsonUtil() {
  }

  /**
   * 将对象转成json字符串
   *
   * @param obj 对象
   * @return json字符串
   */
  public static String toJson(Object obj) {
    return GSON.toJson(obj);
  }

  /**
   * 将json转成bean对象
   *
   * @param json json字符串
   * @param cls  bean对象的类型
   * @param <T>  bean对象
   * @return bean对象
   */
  public static <T> T jsonToBean(String json, Class<T> cls) {
    return GSON.fromJson(json, cls);
  }

  /**
   * 将json转成list对象
   *
   * @param json json字符串
   * @param cls  bean对象的类型
   * @param <T>  bean对象
   * @return bean对象列表
   */
  public static <T> List<T> jsonToList(String json, Class<T> cls) {
    return GSON.fromJson(json, new TypeToken<List<T>>() {
    }.getType());
  }

  /**
   * 将json转成list中有map的
   *
   * @param json json字符串
   * @param <T>  bean对象
   * @return list列表
   */
  public static <T> List<Map<String, T>> jsonToListMaps(String json) {
    return GSON.fromJson(json, new TypeToken<List<Map<String, T>>>() {
    }.getType());
  }

  /**
   * 将json转成map
   * @param json json字符串
   * @param <T> bean对象
   * @return map对象
   */
  public static <T> Map<String, T> jsonToMap(String json) {
    return GSON.fromJson(json, new TypeToken<Map<String, T>>(){}.getType());
  }
}
