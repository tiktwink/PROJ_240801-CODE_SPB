package com.np.demojwt.mapper;

import com.alibaba.fastjson.JSON;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.np.demojwt.entity.Import;
import com.np.demojwt.entity.vo.ProductVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.List;

@Service
public class ImportServiceImpl extends ServiceImpl<ImportMapper, Import> implements ImportService {
  
  @Resource
  private ImportMapper importMapper;
  
  @Override
  public Integer addImport(Import im) {
    
    List<ProductVo> productVoList = im.getImportContent();
    
    double totalCost = 0;
    for (ProductVo productVo : productVoList) {
      totalCost += (productVo.getPrice() * productVo.getCount());
    }
    im.setTotalCost(totalCost);
    
    int totalCount = 0;
    for (ProductVo productVo : productVoList) {
      totalCount += productVo.getCount();
    }
    im.setTotalCount(totalCount);
    if (im.getOffRate() == null) im.setOffRate(0.0);
    
    im.setImportId(null);
    im.setTotalCostAfterOff(null); //数据库自动执行计算  im.setTotalCostAfterOff(im.getTotalCost()*(1-im.getOffRate()));
    
    im.setImportContentJstring(JSON.toJSONString(productVoList));
    
    int i = importMapper.insertSelective(im);
    if (i > 0) return im.getImportId();
    else return null;
  }
}
