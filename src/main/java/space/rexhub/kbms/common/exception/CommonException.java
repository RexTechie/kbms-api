package space.rexhub.kbms.common.exception;

import space.rexhub.kbms.common.constant.CommonStatus;

import java.io.Serializable;

/**
 * 自定义通用异常类
 * @author Rex
 * @date 2021-06-01 15:37
 */
public class CommonException extends RuntimeException implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 错误码
   */
  private Integer code;

  /**
   * 错误信息
   */
  private String msg;

  public CommonException(Integer code, String msg){
    super(msg);
    this.code = code;
    this.msg = msg;
  }
  public CommonException(CommonStatus commonStatus){
    super(commonStatus.getMsg());
    this.code = commonStatus.getCode();
    this.msg = commonStatus.getMsg();
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
