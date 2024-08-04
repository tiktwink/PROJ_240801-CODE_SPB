package com.np.demojwt.controller;

import com.alibaba.fastjson.JSON;
import com.mybatisflex.core.query.QueryChain;
import com.np.demojwt.entity.Export;
import com.np.demojwt.entity.Import;
import com.np.demojwt.entity.vo.ProductVo;
import com.np.demojwt.mapper.ImportService;
import com.np.demojwt.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.np.demojwt.entity.table.ImportTableDef.IMPORT;

@RestController
@RequestMapping("/import")
public class ImportController {
  
  @Resource
  private ImportService importService;
  
  @PostMapping("/add")
  public Result addImport(@RequestBody Import im) {
//    System.out.println(im);
    boolean b = importService.addImport(im);
    
    if (b) return Result.success("添加成功");
    else return Result.fail("添加失败");
  }
  
  @PostMapping("/log")
  public Result getImportLog(@RequestBody Import ex) {
    
    if (ex.getImportId() != null && !ex.getImportId().isBlank()) {
      Import export = importService.getById(ex.getImportId());
      return Result.success("查询成功", List.of(export));
    } else {
      List<Import> list = importService.listLog(ex);
      if (list != null) return Result.success("查询成功", list);
      else return Result.fail("查询失败");
    }
  }
  
  @GetMapping("/detail/{id}")
  public Result getExportDetail(@PathVariable String id) {
    if (id == null || id.isBlank()) return Result.fail("参数错误");
    
    //‼️注意：Export中的exportContentJstring字段为json，被标记为isLarge=true，这里若要查询，需要指定ALL_COLUMNS
    Import export = QueryChain.of(Import.class).select(IMPORT.ALL_COLUMNS)
                        .where(IMPORT.IMPORT_ID.eq(id)).one();
    if (export == null) return Result.fail("查询失败");
    
    String exportContentJstring = export.getImportContentJstring(); //获取JSON格式字符串
    //‼️反序列化，List<ProductVo>反序列化使用JSON.parseArray()
    export.setImportContent(JSON.parseArray(exportContentJstring, ProductVo.class));
    
    export.setImportContentJstring(null);
    return Result.success("查询成功", export.getImportContent());
  }
  
}
