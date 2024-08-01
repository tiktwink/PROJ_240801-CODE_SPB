package com.np.demojwt.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.CommaSplitTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("user")
public class User implements Serializable {
  
  @Serial
  private static final long serialVersionUID = 1L;
  
  @Id(keyType = KeyType.None)
  private String username;
  @JSONField(serialize = false) //不要序列化，但查询时正常查询
  private String password;
  private String usernick;
  
}
