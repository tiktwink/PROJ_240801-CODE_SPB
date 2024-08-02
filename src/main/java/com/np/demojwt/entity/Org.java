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
@Table("org")
public class Org implements Serializable {
  
  @Serial
  private static final long serialVersionUID = 3L;
  
  @Id(keyType = KeyType.Auto)
  private String orgId;
  private String orgType;
  private String orgName;
}
