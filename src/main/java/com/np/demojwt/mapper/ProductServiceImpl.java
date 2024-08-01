package com.np.demojwt.mapper;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.np.demojwt.entity.Product;
import com.np.demojwt.util.Result;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.np.demojwt.entity.table.ProductTableDef.PRODUCT;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
  
  @Mapper
  private ProductMapper productMapper;
  
  @Override
  public Result getProductList(Product product) {
    //对于like查询，将无效值滤去（mybatisflex自动过滤null条件）
    if (product.getProductName().trim().isBlank()) product.setProductName(null);
    else product.setProductName("%" + product.getProductName() + "%");
    if (product.getProductSpec().trim().isBlank()) product.setProductSpec(null);
    else product.setProductSpec("%" + product.getProductSpec() + "%");
    if (product.getProductCode().trim().isBlank()) product.setProductCode(null);
    else product.setProductCode("%" + product.getProductCode() + "%");
    
    //这里没有采用分页查询，偷懒了
    List<Product> productList = QueryChain.of(Product.class)
                                    .select(PRODUCT.ALL_COLUMNS).where(PRODUCT.PRODUCT_NAME.like(product.getProductName()))
                                    .and(PRODUCT.PRODUCT_SPEC.like(product.getProductSpec()))
                                    .and(PRODUCT.PRODUCT_CODE.like(product.getProductCode())).list();
    
    if (productList.isEmpty()) return Result.fail("没有找到相关数据");
    else return Result.success(productList);
    
  }
}
