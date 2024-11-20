package space.rexhub.kbms.common.constant;


/**
 * 状态码枚举
 */
public enum CommonStatus {
  /**
   * 状态码-状态信息
   */
  SUCCESS(10000, "SUCCESS"),
  FAILED(10001, "FAILED"),
  SERVER_ERROR(10002, "服务器异常"),
  SYSTEMIC_CAN_NOT_ALTER(10003, "不能操作系统内置对象"),
  NOT_ALLOWED(10004, "没有权限"),
  INSERT_ERROR(10005, "新增失败"),
  DELETE_ERROR(10006, "删除失败"),
  UPDATE_ERROR(10007, "更新失败"),
  NAME_EXISTS(10008, "名字已存在"),
  UPLOAD_ERROR(10009, "上传失败"),
  NOT_LOGIN(10010, "未登录，或登录超时，请重新登录"),
  LOGIN_ERROR(10011, "登录失败，账号或密码错误"),
  LOGOUT_ERROR(10012, "登出失败"),
  EXISTS_REF_ERROR(10013, "存在引用，无法删除"),
  RESET_PASSWORD_ERROR(10014, "重置密码失败"),
  OLD_PASSWORD_ERROR(10015, "旧密码不匹配"),
  USER_NOT_EXIST(10016, "用户不存在"),
  ASSIGN_PERM_ERROR(10017, "分配权限错误"),
  ID_NOT_EXISTS(10018, "ID不存在"),
  ;

  /**
   * 状态码
   */
  Integer code;

  /**
   * 状态信息
   */
  String msg;

  CommonStatus(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
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
