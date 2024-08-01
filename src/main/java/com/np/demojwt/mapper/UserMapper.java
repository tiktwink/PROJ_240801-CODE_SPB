package com.np.demojwt.mapper;


import com.mybatisflex.core.BaseMapper;

import com.np.demojwt.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
