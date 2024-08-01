package com.np.demojwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;


@Slf4j
@Component
public class TokenUtil { //令牌的生成和验证
  
  public static final Integer EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000; //❗默认有效期为14天
  private static final String SIGNATURE = "!^&%&*!@$*%!!@(&%2ar^2lijp"; //❗签名
  
  /**
   * 生成Token(指定有效期，如不指定，则使用默认有效期)
   */
  public static String generateToken(Map<String, Object> map, Integer expireTiming) {
    JWTCreator.Builder builder = JWT.create();
    
    // 配置令牌有效期
    if (expireTiming == null) {
      expireTiming = EXPIRE_TIME; //❗值为null，则使用默认有效期
    } else if (expireTiming <= 60 * 1000) {
      expireTiming = 60 * 1000; //❗有效期不少于60秒，这是自定义的策略，可以根据实际情况调整
    }
    Calendar instance = Calendar.getInstance();
    instance.add(Calendar.MILLISECOND, Math.toIntExact(expireTiming)); //有效期
    
    map.forEach((k, v) -> {
      builder.withClaim(k, String.valueOf(v));
    });
    
    return builder.withExpiresAt(instance.getTime())
               .sign(Algorithm.HMAC256(SIGNATURE));
  }
  
  /**
   * 生成Token(365天有效期)
   */
  public static String generate365DaysToken(Map<String, Object> map) {
    JWTCreator.Builder builder = JWT.create();
    
    // 配置令牌有效期
    Calendar instance = Calendar.getInstance();
    instance.add(Calendar.DATE, 365); //过期时间为365天
    // instance.add(Calendar.MINUTE, 1); //过期时间为1分钟
    
    map.forEach((k, v) -> {
      builder.withClaim(k, String.valueOf(v));
    });
    
    return builder.withExpiresAt(instance.getTime())
               .sign(Algorithm.HMAC256(SIGNATURE));
  }
  
  /**
   * 生成Token(30天有效期)
   */
  public static String generate30DaysToken(Map<String, Object> map) {
    JWTCreator.Builder builder = JWT.create();
    
    // 配置令牌有效期
    Calendar instance = Calendar.getInstance();
    instance.add(Calendar.DATE, 3); //过期时间为3天
    // instance.add(Calendar.MINUTE, 1); //过期时间为1分钟
    
    map.forEach((k, v) -> {
      builder.withClaim(k, String.valueOf(v));
    });
    
    return builder.withExpiresAt(instance.getTime())
               .sign(Algorithm.HMAC256(SIGNATURE));
  }
  
  /**
   * 生成Token(12小时有效期)
   */
  public static String generate12HoursToken(Map<String, Object> map) {
    JWTCreator.Builder builder = JWT.create();
    
    // 配置令牌有效期
    Calendar instance = Calendar.getInstance();
    instance.add(Calendar.HOUR, 12); //过期时间为12小时
    // instance.add(Calendar.MINUTE, 1); //过期时间为1分钟
    
    map.forEach((k, v) -> {
      builder.withClaim(k, String.valueOf(v));
    });
    
    return builder.withExpiresAt(instance.getTime())
               .sign(Algorithm.HMAC256(SIGNATURE));
  }
  
  /**
   * 验证token
   */
  public TokenValid verify(String token) {
    try {
      //尝试解析令牌
      DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
      log.info("|>> ✅token valid: " + new Date());
     
      TokenValid tokenValid = new TokenValid();
      tokenValid.setValid(true);
      tokenValid.setMsg("token valid");
      tokenValid.setClaims(verify.getClaims());
      
      //从解析结果中提取用户名
      String username = verify.getClaim("username").asString();
      if (username == null || username.isEmpty()) {
        throw new RuntimeException("username not found in token");
      } else {
        tokenValid.setUsername(username);
      }
      
      return tokenValid;
      
    } catch (TokenExpiredException e) {
      e.printStackTrace();
//      log.info("令牌过期");
      TokenValid tokenValid = new TokenValid();
      tokenValid.setValid(false);
      tokenValid.setMsg("token expired");
      log.info("|>> ❌token error: {}", tokenValid.getMsg());
      return tokenValid;
      
    } catch (Exception e) {
      e.printStackTrace();
//      log.info("令牌异常");
      TokenValid tokenValid = new TokenValid();
      tokenValid.setValid(false);
      tokenValid.setMsg("token exception");
      log.info("|>> ❌token error: {}", tokenValid.getMsg());
      return tokenValid;
    }
  }
  
  /**
   * 实际验证token的方法，并封装TokenValid对象
   */
  /*private TokenValid getTokenValid(DecodedJWT verify, String token) {
    TokenValid tokenValid = new TokenValid();
    try {
      MaskManager.skipMask(); //🚫暂停数据脱敏
      
      String username = String.valueOf(verify.getClaim("username")); //🚫如果username不在令牌载荷中，此时其值将会是 Null Claim//    System.out.println(verify.getClaims());
      username = username.substring(1, username.length() - 1);
//    Map<String, Claim> claims = verify.getClaims(); //获取所有携带数据
//    String username = claims.get("username").toString();
      if ("Null Claim".equals(username) || username == null || "".equals(username)) {
        log.info("|>> ❌username cliamed in token is empty");
        tokenValid.setValid(false);
        tokenValid.setMsg("token error");
        return tokenValid;
      }
      tokenValid.setUsername(username);
      
      User user = userMapper.selectOneByCondition(UserTableDef.USER.USERNAME.eq(username));
      if (user == null) {
        log.info("|>> ❌username unselected in database");
        tokenValid.setValid(false);
        tokenValid.setMsg("token error");
        return tokenValid;
      }
      String md5_token = user.getToken();
//      if (!token.equals(md5_token)) {
      if (!Md5Util.match(token, md5_token)) {
        log.info("|>> ❌token unmatched");
        tokenValid.setValid(false);
        tokenValid.setMsg("token error");
        return tokenValid;
      }
      log.info("|>> ✅token carried by account: {} is matched, so MyHandlerInterceptor will pass its request...", username);
      log.info("|>> ℹ️while it is possible to get further check later in which there are other handlers to affect");
      tokenValid.setValid(true);
      tokenValid.setMsg("token valid");
      tokenValid.setClaims(verify.getClaims());
      return tokenValid;
    } catch (Exception e) {
      e.printStackTrace();
      tokenValid.setMsg("error occurs");
      return tokenValid;
    } finally {
      MaskManager.restoreMask(); //🚫恢复数据脱敏
    }
  }*/
}
