package com.np.demojwt.mapper;

import com.mybatisflex.core.service.IService;
import com.np.demojwt.entity.User;
import com.np.demojwt.util.Result;

public interface UserService extends IService<User> {
  
  //⬇️以下是自定义的业务逻辑接口
  Result login(User user);
  
 
}
