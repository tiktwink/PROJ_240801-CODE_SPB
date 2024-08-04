package com.np.demojwt.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.np.demojwt.entity.vo.ProductVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("import")
public class Import implements Serializable {
  
  @Serial
  private static final long serialVersionUID = 4L;
  
  @Id(keyType = KeyType.None)
  private String importId;
  
  private Long timestamp;
  private Long createTimestamp;
  
  private Integer operatorId;
  private String operatorName;
  private String orgId;
  private String orgName;
  private String payWay;
  private String cangkuName;
  private String backupLink;
  private String notes;
  //这4个值应该在后台业务中计算，不能直接使用前端数据
  private Integer totalCount;
  private Double totalCost;
  private Double offRate;
  private Double totalCostAfterOff;
  
  @Column(isLarge = true)
  private String importContentJstring;
  
  @Column(ignore = true)
  private List<ProductVo> importContent;
  
}
