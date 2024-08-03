package com.np.demojwt.controller;

import com.np.demojwt.entity.Export;
import com.np.demojwt.mapper.ExportService;
import com.np.demojwt.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
public class ExportController {
  
  @Resource
  private ExportService exportService;
  
  @RequestMapping("/add")
  public Result addExport(@RequestBody Export ex) {
    
    Integer id = exportService.addExport(ex);
    
    if(id!=null) return Result.success("添加成功", id);
    else return Result.fail("添加失败");
  }
  
  
}
