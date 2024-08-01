package com.np.demojwt.mapper;


import com.mybatisflex.core.service.IService;
import com.np.demojwt.entity.User;


public interface UserService extends IService<User> {
  
  //⬇️以下是自定义的业务逻辑接口
  String login(User user);
  
 
}
