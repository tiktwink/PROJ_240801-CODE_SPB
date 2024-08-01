package com.np.demojwt.mapper;

import com.mybatisflex.spring.service.impl.ServiceImpl;

import com.np.demojwt.entity.User;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
  
  @Resource
  private UserMapper userMapper;
  
  
  @Override
  public String login(User user) {
    return "";
  }
}
