package com.np.demojwt.mapper;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.np.demojwt.entity.Org;
import com.np.demojwt.util.Result;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import static com.np.demojwt.entity.table.OrgTableDef.ORG;

import java.util.List;

@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {
  
  @Resource
  private OrgMapper orgMapper;
  
  @Override
  public Result getOrgList(Org org) {
    if(org.getOrgName().trim().isBlank()) org.setOrgName(null);
    else org.setOrgName("%" + org.getOrgName() + "%");
    
    //这里没有采用分页查询，偷懒了
    List<Org> orgList = QueryChain.of(orgMapper)
                            .select(ORG.ALL_COLUMNS)
                            .where(ORG.ORG_NAME.like(org.getOrgName()))
                            .and(ORG.ORG_TYPE.eq(org.getOrgType()))
                            .list();
    if(orgList.isEmpty()) return Result.fail("没有找到任何数据");
    else return Result.success("查询成功", orgList);
  }
  
  
  
}
