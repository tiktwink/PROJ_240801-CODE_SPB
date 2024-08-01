package com.np.demojwt.util;


import com.auth0.jwt.interfaces.Claim;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenValid {
  Boolean valid = false; //令牌是否有效(true有效，false无效)
  String msg; //令牌是否有效，如果无效，无效的原因是什么
  Map<String, Claim> claims;
  String username = ""; //ℹ️用户名
}
