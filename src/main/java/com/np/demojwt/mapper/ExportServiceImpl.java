package com.np.demojwt.mapper;

import com.alibaba.fastjson.JSON;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.np.demojwt.entity.Export;
import com.np.demojwt.entity.vo.ProductVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportServiceImpl extends ServiceImpl<ExportMapper, Export> implements ExportService {
  @Resource
  private ExportMapper exportMapper;
  
  @Override
  public Integer addExport(Export ex) {
    
    List<ProductVo> productVoList = ex.getExportContent();
    
    double totalCost = 0;
    for (ProductVo productVo : productVoList) {
      totalCost += (productVo.getPrice() * productVo.getCount());
    }
    ex.setTotalCost(totalCost);
    
    int totalCount = 0;
    for (ProductVo productVo : productVoList) {
      totalCount += productVo.getCount();
    }
    ex.setTotalCount(totalCount);
    if (ex.getOffRate() == null) ex.setOffRate(0.0);
    
    ex.setExportId(null);
    ex.setTotalCostAfterOff(null);
    
    ex.setExportContentJstring(JSON.toJSONString(productVoList));
    
    int i = exportMapper.insertSelective(ex);
    if (i > 0) return ex.getExportId();
    else return null;
  }
}
