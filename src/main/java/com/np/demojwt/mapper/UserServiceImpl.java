package com.np.demojwt.mapper;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.np.demojwt.entity.User;
import com.np.demojwt.util.Result;
import com.np.demojwt.util.TokenUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static com.np.demojwt.entity.table.UserTableDef.USER;

import java.util.HashMap;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
  
  @Resource
  private UserMapper userMapper;
  
  @Override
  public Result login(User user) {
    if (user.getUsername().isBlank() || user.getPassword().isBlank()) {
      return Result.fail("用户名或密码不能为空");
    }
    
    User user1 = userMapper.selectOneByCondition(USER.USERNAME.eq(user.getUsername()).and(USER.PASSWORD.eq(user.getPassword())));
    
    if (user1 != null) return Result.success("登录成功", this.grantToken(user.getUsername()));
    return Result.fail("用户名或密码错误");
  }
  
  public String grantToken(String username) {
    return TokenUtil.generate365DaysToken(new HashMap<String, Object>() {{
      put("username", username);
      put("expireTiming", "365DAY");
      put("generateMills", System.currentTimeMillis());
    }});
  }
  
}
