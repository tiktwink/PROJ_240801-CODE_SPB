package com.np.demojwt.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("product")
public class Product implements Serializable {
  
  @Serial
  private static final long serialVersionUID = 2L;
  
  @Id(keyType = KeyType.Auto)
  private Integer productId;
  
  private String productName;
  private String productCode;
  private String productSpec;
  private String productUnit;
  private Integer productCount;
}
