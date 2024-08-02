package com.np.demojwt.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo {
  private Integer productId;
  private String productName;
  private String productCode;
  private String productSpec;
  private String productUnit;
  
  private String contactId;
  private Integer count;
  private Double price;
  private Double cost;
}
