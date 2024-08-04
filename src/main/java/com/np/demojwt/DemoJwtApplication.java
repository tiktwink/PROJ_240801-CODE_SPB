package com.np.demojwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@MapperScan("com.np.demojwt.mapper") //如果要启用该行，则需要将mapper目录中的非Mapper类移到其他目录
public class DemoJwtApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(DemoJwtApplication.class, args);
  }
  
}
