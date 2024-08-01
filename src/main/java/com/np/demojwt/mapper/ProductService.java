package com.np.demojwt.mapper;

import com.mybatisflex.core.service.IService;
import com.np.demojwt.entity.Product;
import com.np.demojwt.util.Result;

public interface ProductService extends IService<Product> {
  Result getProductList(Product product);
}
