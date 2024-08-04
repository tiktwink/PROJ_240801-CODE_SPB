package com.np.demojwt.mapper;

import com.alibaba.fastjson.JSON;
import com.mybatisflex.core.query.*;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.np.demojwt.entity.Export;
import com.np.demojwt.entity.Product;
import com.np.demojwt.entity.vo.ProductVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static com.np.demojwt.entity.table.ExportTableDef.EXPORT;
import static com.np.demojwt.entity.table.ProductTableDef.PRODUCT;

import java.util.List;
import java.util.Optional;

@Service
public class ExportServiceImpl extends ServiceImpl<ExportMapper, Export> implements ExportService {
  @Resource
  private ExportMapper exportMapper;
  
  @Override
  public boolean addExport(Export ex) {
    
    List<ProductVo> productVoList = ex.getExportContent();
    
    try {
      for (ProductVo productVo : productVoList) {
        Product product = QueryChain.of(Product.class).select(PRODUCT.PRODUCT_COUNT)
                              .where(PRODUCT.PRODUCT_ID.eq(productVo.getProductId()))
                              .one();
        
        if (product == null || productVo.getCount() > product.getProductCount()) {
          throw new RuntimeException("库存不足，或产品ID不存在"); // 产品ID错误，或库存不足
        } else {
          UpdateChain.of(Product.class).set(PRODUCT.PRODUCT_COUNT, PRODUCT.PRODUCT_COUNT.subtract(productVo.getCount()))
              .where(PRODUCT.PRODUCT_ID.eq(productVo.getProductId()))
              .update();
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("更新失败");
    }
    
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
    ex.setTotalCostAfterOff(ex.getTotalCost() * (1 - ex.getOffRate() / 100.0));
    //‼️序列化
    ex.setExportContentJstring(JSON.toJSONString(productVoList));
    
    int i = exportMapper.insertSelective(ex);
    return i > 0;
  }
  
  @Override
  public List<Export> listLog(Export ex) {
    try {
      // 动态化查询构建1
      boolean flag = ex.getTimestamp() != null;
      
      return exportMapper.selectListByQuery(
          new QueryWrapper().where(EXPORT.TIMESTAMP.between(ex.getTimestamp(), sumLongs(ex.getTimestamp(), 30 * 60 * 60 * 24L), flag))
              .and(EXPORT.CREATE_TIMESTAMP.eq(ex.getCreateTimestamp()))
              .and(EXPORT.OPERATOR_ID.eq(ex.getOperatorId(), If::notNull)) // 动态化查询构建2
              .and(EXPORT.ORG_ID.eq(ex.getOrgId(), If::hasText)) // 动态化查询构建3
      );
      
    } catch (Exception e) {
      return null;
    }
  }
  
  public static Long sumLongs(Long num1, Long num2) {
    // 使用 Optional 处理可能为 null 的情况
    Optional<Long> optNum1 = Optional.ofNullable(num1);
    Optional<Long> optNum2 = Optional.ofNullable(num2);
    
    // 如果两个值都不为 null，则相加并返回结果
    if (optNum1.isPresent() && optNum2.isPresent()) {
      return optNum1.get() + optNum2.get();
    }
    
    // 如果有一个或两个值为 null，则返回 null
    return null;
  }
}
