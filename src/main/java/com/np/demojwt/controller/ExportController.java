package com.np.demojwt.controller;

import com.alibaba.fastjson.JSON;
import com.mybatisflex.core.query.QueryChain;
import com.np.demojwt.entity.Export;
import com.np.demojwt.entity.vo.ProductVo;
import com.np.demojwt.mapper.ExportService;
import com.np.demojwt.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import static com.np.demojwt.entity.table.ExportTableDef.EXPORT;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/export")
public class ExportController {
  
  @Resource
  private ExportService exportService;
  
  @PostMapping("/add")
  public Result addExport(@RequestBody Export ex) {
    
    boolean b = exportService.addExport(ex);
    
    if (b) return Result.success("添加成功");
    else return Result.fail("添加失败");
  }
  
  
  @PostMapping("/log")
  public Result getExportLog(@RequestBody Export ex) {
    
    if (ex.getExportId() != null && !ex.getExportId().isBlank()) {
      Export export = exportService.getById(ex.getExportId());
      if (export != null) return Result.success("查询成功", List.of(export));
      else return Result.fail("查询失败");
    } else {
      List<Export> list = exportService.listLog(ex);
      if (list != null && !list.isEmpty()) return Result.success("查询成功", list);
      else return Result.fail("查询失败");
    }
  }
  
  @GetMapping("/detail/{id}")
  public Result getExportDetail(@PathVariable String id) {
    if(id==null||id.isBlank()) return Result.fail("参数错误");
    
    //‼️注意：Export中的exportContentJstring字段为json，被标记为isLarge=true，这里若要查询，需要指定ALL_COLUMNS
    Export export = QueryChain.of(Export.class).select(EXPORT.ALL_COLUMNS)
                        .where(EXPORT.EXPORT_ID.eq(id)).one();
    if(export==null) return Result.fail("查询失败");
    
    String exportContentJstring = export.getExportContentJstring(); //获取JSON格式字符串
    //‼️反序列化，List<ProductVo>反序列化使用JSON.parseArray()
    export.setExportContent(JSON.parseArray(exportContentJstring, ProductVo.class));
    
    export.setExportContentJstring(null);
    return Result.success("查询成功", export.getExportContent());
  }
}
