package com.np.demojwt.controller;

import com.np.demojwt.entity.User;
import com.np.demojwt.mapper.UserMapper;
import com.np.demojwt.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.np.demojwt.entity.table.UserTableDef.USER;

@RestController
@RequestMapping("/query")
public class QueryController {
  
  @Resource
  private UserMapper userMapper;
  
  @GetMapping("/try-identify/{username}")
  public Result tryIdentify(@PathVariable String username) {
    System.out.println("tryIdentify");
    User user = userMapper.selectOneById(username);
    if (user != null) {
      return Result.success("查询用户成功",user.getUsernick());
    }
    return Result.fail("用户不存在");
  }
}
