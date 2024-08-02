package com.np.demojwt.controller;

import com.np.demojwt.entity.Product;
import com.np.demojwt.mapper.ProductService;
import com.np.demojwt.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
  
  @Resource
  private ProductService productService;
  
  @PostMapping("/search")
  public Result getProductList(@RequestBody Product product){
    if(product.getProductId() != null){
      Product prod = productService.getById(product.getProductId());
      if(prod != null) return Result.success(List.of(prod));
      else return Result.fail("没有找到产品信息");
    }
    
    return productService.getProductList(product);
  }
  
  @PostMapping("/add")
  public Result addProduct(@RequestBody Product product){
    boolean b  = productService.save(product) ;
    if(b) return Result.success("Product added successfully",product.getProductId());
    else return Result.fail("Product not added");
  }
  
  @PostMapping("/update")
  public Result updateProduct(@RequestBody Product product){
    boolean b = productService.updateById(product);
    if(b) return Result.success("Product updated successfully",product);
    else return Result.fail("Product not updated");
  }
  
}
