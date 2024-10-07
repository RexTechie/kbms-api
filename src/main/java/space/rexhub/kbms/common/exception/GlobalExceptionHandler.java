package space.rexhub.kbms.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.model.vo.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一异常处理
 * @author Rex
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * 服务器错误
   * @param e 异常
   * @return 异常信息
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(Exception.class)
  public Result<Void> handleException(Exception e){
    logger.error("======Default Exception Class: {}", e.toString());
    logger.error("======Default Exception Msg: {}" ,e.getMessage());
    logger.error("======Default Exception:", e);
    return Result.error(CommonStatus.SERVER_ERROR.getCode(), e.getMessage());
  }

  /**
   * 处理自定义异常
   * @param e 自定义异常
   * @return 异常信息
   */
  @ExceptionHandler(CommonException.class)
  public Result<Void> handleCommonException(CommonException e){
    logger.error("BizException Class: {}", e.toString());
    logger.error("BizException Msg: {}", e.getMsg());
    logger.error("BizException:", e);
    return Result.error(e);
  }

  /**
   * 处理校验异常
   * @param e 校验异常
   * @return 异常信息
   */
  @ExceptionHandler(BindException.class)
  public Result<Void> handleBindException(BindException e){
    logger.error("BindException Class:" + e);
    logger.error("BindException Msg:" + e.getMessage());
    logger.error("BindException:", e);
    return handleBindingResult(e.getBindingResult());
  }

  /**
   * 处理校验结果
   * @param bindingResult 校验结果
   * @return 返回校验结果
   */
  private Result<Void> handleBindingResult(BindingResult bindingResult) {
    //把异常处理为对外暴露的提示
    List<String> list = new ArrayList<>();
    if (bindingResult.hasErrors()) {
      List<ObjectError> allErrors = bindingResult.getAllErrors();
      for (ObjectError objectError : allErrors) {
        String message = objectError.getDefaultMessage();
        list.add(message);
      }
    }
    if (list.size() == 0){
      return Result.error(CommonStatus.FAILED);
    }
    // 去掉list的中括号
    String listStr = list.toString();
    String errMsg = listStr.substring(1, listStr.length()-1);
    return Result.error(CommonStatus.FAILED.getCode(), errMsg);
  }
}
