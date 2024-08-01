package com.np.demojwt.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
  
  private Integer code = null; //响应码
  private String status = null; //响应状态文本
  private String msg = null; //响应消息
  private Object data = null; //响应数据
  
  public static Result enumBuild(ResultCodeEnum resultCodeEnum) {
    Result result = new Result();
    
    result.setCode(resultCodeEnum.getCode());
    result.setMsg(resultCodeEnum.getMessage());
    result.setData(null);
    
    return result;
  }
  
  public static Result enumBuild(ResultCodeEnum resultCodeEnum, Object data) {
    Result result = new Result();
    
    result.setCode(resultCodeEnum.getCode());
    result.setMsg(resultCodeEnum.getMessage());
    result.setData(data);
    
    return result;
  }
  
  public static Result error(String msg) {
    return new Result(-1, "error", msg, null);
  }
  
  public static Result success(String msg) {
    return new Result(0, "success", msg, null);
  }
  
  public static Result success(Object data) {
    return new Result(0, "success", null, data);
  }
  
  public static Result success(String msg, Object data) {
    return new Result(0, "success", msg, data);
  }
  
  public static Result success(Integer code, String msg, Object data) {
    return new Result(code, "success", msg, data);
  }
  
  public static Result fail(String msg) {
    return new Result(1, "fail", msg, null);
  }
  
  public static Result fail(Object data) {
    return new Result(1, "fail", null, data);
  }
  
  public static Result fail(String msg, Object data) {
    return new Result(1, "fail", msg, data);
  }
  
  public static Result fail(Integer code, String msg, Object data) {
    return new Result(code, "fail", msg, data);
  }
  
  
}
