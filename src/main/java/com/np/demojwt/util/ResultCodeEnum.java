package com.np.demojwt.util;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 */
@Getter
public enum ResultCodeEnum {
  
  SUCCESS(0, "成功"),
  FAIL(1, "失败"),
  ERROR(-1, "异常"),
  SERVICE_ERROR(2012, "服务异常"),
  DATA_ERROR(204, "数据异常"),
  ILLEGAL_REQUEST(205, "非法请求"),
  REPEAT_SUBMIT(206, "重复提交"),
  ARGUMENT_VALID_ERROR(210, "参数校验异常"),
  
  LOGIN_AUTH(208, "请先登录"),
  PERMISSION(209, "没有权限"),
  CACHEEXPIRED(210, "缓存过期"),
  TOKENEXPIRED(50014, "登录过期,请重新登录"),
  TOKENEXPIREDBYMENU(300, "登录过期,请重新登录"),
  ACCOUNT_ERROR(214, "账号不正确"),
  PASSWORD_ERROR(215, "密码不正确"),
  LOGIN_MOBLE_ERROR(216, "账号或密码错误"),
  ACCOUNT_STOP(217, "账号已停用"),
  NODE_ERROR(218, "该节点下有子节点，不可以删除"),
  
  UPDATE_ERROR(220, "并发修改异常"),
  
  UPLOAD_ERROR(219, "上传文件失败");
  
  private Integer code;
  
  private String message;
  
  private ResultCodeEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
