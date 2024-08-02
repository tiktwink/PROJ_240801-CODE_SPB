package com.np.demojwt.controller;

import com.np.demojwt.entity.Import;
import com.np.demojwt.mapper.ImportService;
import com.np.demojwt.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
public class ImportController {
  
  @Resource
  private ImportService importService;
  
  @PostMapping("/add")
  public Result addImport(@RequestBody Import im) {
    System.out.println(im);
    Integer id = importService.addImport(im);
    
    if (id != null) return Result.success("添加成功", id);
    else return Result.fail("添加失败");
  }
}
