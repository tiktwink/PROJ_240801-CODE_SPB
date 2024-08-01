package com.np.demojwt.filter;

import com.auth0.jwt.interfaces.Claim;
import com.np.demojwt.util.TokenUtil;
import com.np.demojwt.util.TokenValid;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;


@Component
@Order(1)
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
  @Resource
  private TokenUtil tokenUtil;
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    
    String uri = request.getRequestURI(); // URI: /hello, URL: localhost:8080/hello
    System.out.println("uri: " + uri);
    
    
    try { //1. ❗放行接口
      if (!uri.equals("/user/login") && !uri.startsWith("/query")) {
        System.out.println("即将校验token");
        //2. 校验token、认证
        this.validateToken(request);
        System.out.println("token校验、认证成功");
      }
    } catch (Exception e) {
      // System.out.println("捕获到AuthenticationException异常🚫🚫🚫");
      throw new RuntimeException();//❗捕获到异常后，应该终止对请求的处理
    }
    //若上一步抛出异常此处不会再放行，直接返回错误信息
    //3. 放行（继续后续过滤器）
    filterChain.doFilter(request, response);
    
  }
  
  //❗token校验、认证
  private void validateToken(HttpServletRequest request) {
    //1. 获取并解析token
    String token = request.getHeader("Authorization");
    if (token == null || token.isEmpty()) {
      throw new RuntimeException("token is empty");
    }
    TokenValid tokenValid = new TokenValid();
    try {
      tokenValid = tokenUtil.verify(token);
    } catch (Exception e) {
      throw new RuntimeException("unknown error");
    }
    
    //2. 在Redis中查询token是否存在
    /*String tokenInRedis = this.stringRedisTemplate.opsForValue().get("token-" + tokenValid.getUsername());
    if (tokenInRedis == null || tokenInRedis.isEmpty()) {
      throw new MyAuthenticationException("token not exists in Redis"); //token不存在于Redis中
    } else if (!tokenInRedis.equals(token)) {
      throw new MyAuthenticationException("token exists but not matched"); //token存在于Redis中但不匹配
    }*/
    
    //3. 提取token负载信息(username)
    /*Map<String, Claim> claims = tokenValid.getClaims();
    String username = claims.get("username").asString(); //❗将Claim对象转String
    String usernameInRequest = request.getHeader("username");
    if (!username.equals(usernameInRequest)) {
      throw new RuntimeException("username not matched");
    }*/
    
    //TODO‼️额外添加一步：如果token验证成功，但是即将过期，则为用户创建新的token并返回
    // ‼️方案一：将本函数返回值由void改为String，如果不需要刷新token这返回null，否则返回新token，缺点是每次鉴权请求中都要附带一次token过期校验，且只对需要鉴权的接口有效，对于不需要鉴权的接口，该方案不会校验token，自然也不会校验token是否过期
    // ‼️方案二：额外创建一个sus-login接口，由客户端主动请求本接口，获取是否需要刷新以及刷新后的token，最好是客户端每次启动时发起该请求，优点是由客户端掌握主动，不需要服务端发起该业务，缺点是要维护一个额外的接口，可能会带来更多的服务器压力
  }
}
