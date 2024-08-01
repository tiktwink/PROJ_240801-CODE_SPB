package com.np.demojwt.controller;

import com.np.demojwt.entity.User;
import com.np.demojwt.mapper.UserService;
import com.np.demojwt.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  
  
  @Resource
  private UserService userService;
  
  
  @RequestMapping("/login")
  public Result login(@RequestBody User user) {
    
    return userService.login(user);
  }
}
