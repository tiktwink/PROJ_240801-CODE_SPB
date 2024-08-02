package com.np.demojwt.controller;

import com.np.demojwt.entity.Org;
import com.np.demojwt.entity.Product;
import com.np.demojwt.mapper.OrgService;
import com.np.demojwt.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/org")
public class OrgController {
  
  
  @Resource
  private OrgService orgService;
  
  @PostMapping("/search")
  public Result getOrgList(@RequestBody Org org) {
    
    if(!org.getOrgId().isBlank()){
      Org o = orgService.getById(org.getOrgId());
      if(o != null) return Result.success(List.of(o));
      else return Result.fail("没有找到组织信息");
    }
    
    return orgService.getOrgList(org);
  }
  
  @PostMapping("/add")
  public Result addOrg(@RequestBody Org org) {
   
   boolean b = orgService.save(org);
   if(b) return Result.success("添加成功", org.getOrgId());
   else return Result.fail("添加失败");
  }
  
  @PostMapping("/update")
  public Result updateOrg(@RequestBody Org org) {
    
    boolean b = orgService.updateById(org);
    if(b) return Result.success("修改成功", org);
    else return Result.fail("修改失败");
  }
  
}
