package com.np.demojwt.mapper;

import com.alibaba.fastjson.JSON;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.np.demojwt.entity.Import;
import com.np.demojwt.entity.Product;
import com.np.demojwt.entity.vo.ProductVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static com.np.demojwt.entity.table.ImportTableDef.IMPORT;
import static com.np.demojwt.entity.table.ProductTableDef.PRODUCT;

@Service
public class ImportServiceImpl extends ServiceImpl<ImportMapper, Import> implements ImportService {
  
  @Resource
  private ImportMapper importMapper;
  
  @Resource
  private ProductMapper productMapper;
  
  @Override
  public boolean addImport(Import im) {
    
    List<ProductVo> productVoList = im.getImportContent();
    
    try {
      for(ProductVo productVo : productVoList){
        UpdateChain.of(Product.class).set(PRODUCT.PRODUCT_COUNT, PRODUCT.PRODUCT_COUNT.add(productVo.getCount()))
            .where(PRODUCT.PRODUCT_ID.eq(productVo.getProductId()))
            .update();
      }
    }catch (Exception e) {
      throw new RuntimeException("更新失败");
    }
    
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
    im.setTotalCostAfterOff(im.getTotalCost()*(1- im.getOffRate()/100.0)); //数据库自动执行计算  im.setTotalCostAfterOff(im.getTotalCost()*(1-im.getOffRate()));
    
    im.setImportContentJstring(JSON.toJSONString(productVoList));
    
    int i = importMapper.insertSelective(im);
    return i > 0;
  }
  
  @Override
  public List<Import> listLog(Import ex) {
    
    try {
      // 动态化查询构建1
      boolean flag = ex.getTimestamp() != null;
      
      return importMapper.selectListByQuery(
          new QueryWrapper().where(IMPORT.TIMESTAMP.between(ex.getTimestamp(), sumLongs(ex.getTimestamp(), 30 * 60 * 60 * 24L), flag))
              .and(IMPORT.CREATE_TIMESTAMP.eq(ex.getCreateTimestamp()))
              .and(IMPORT.OPERATOR_ID.eq(ex.getOperatorId(), If::notNull)) // 动态化查询构建2
              .and(IMPORT.ORG_ID.eq(ex.getOrgId(), If::hasText)) // 动态化查询构建3
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
