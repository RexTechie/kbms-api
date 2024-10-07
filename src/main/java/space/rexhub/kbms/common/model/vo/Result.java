package space.rexhub.kbms.common.model.vo;

import lombok.Data;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.exception.CommonException;
import space.rexhub.kbms.common.utils.JsonUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * Description: 统一Web响应结果
 *
 * @author Rex
 */
@Data
public class Result<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 响应码
   */
  private Integer code;

  /**
   * 响应信息
   */
  private String msg;

  /**
   * 响应数据
   */
  private T data;

  public Result() {
    this.code = 200;
    this.msg = "success";
  }

  public Result(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Result(CommonStatus commonStatus) {
    this(commonStatus.getCode(), commonStatus.getMsg());
  }

  public static Result<Void> success(){
    return new Result<>();
  }

  public static <R>Result<R> success(R data){
    Result<R> result = new Result<>();
    result.setData(data);
    return result;
  }

  public static Result<Void> error(int code, String msg){
    return new Result<>(code, msg);
  }

  public static Result<Void> error(CommonStatus commonStatus){
    return new Result<>(commonStatus);
  }

  public static Result<Void> error(CommonException commonException){
    return new Result<>(commonException.getCode(), commonException.getMsg());
  }

  /**
   * 以流的方式输出success信息和数据
   * @param httpServletResponse 响应流
   * @param data 数据
   * @throws IOException 异常
   */
  public static void success(HttpServletResponse httpServletResponse, Object data) throws IOException {
    httpServletResponse.setContentType("application/json; charset=UTF-8");
    ServletOutputStream outputStream = httpServletResponse.getOutputStream();

    Result<Object> result = Result.success(data);
    outputStream.write(JsonUtil.toJson(result).getBytes(StandardCharsets.UTF_8));
    outputStream.flush();
    outputStream.close();
  }

  /**
   * 以流的方式输出错误信息
   * @param httpServletResponse 输出流
   * @param commonStatus 错误状态
   * @throws IOException 异常
   */
  public static void error(HttpServletResponse httpServletResponse, CommonStatus commonStatus) throws IOException {
    httpServletResponse.setContentType("application/json; charset=UTF-8");
    ServletOutputStream outputStream = httpServletResponse.getOutputStream();
    Result<Void> result = Result.error(commonStatus);
    outputStream.write(JsonUtil.toJson(result).getBytes(StandardCharsets.UTF_8));
    outputStream.flush();
    outputStream.close();
  }
}