package com.np.demojwt.mapper;

import com.mybatisflex.core.service.IService;
import com.np.demojwt.entity.Org;
import com.np.demojwt.util.Result;

public interface OrgService extends IService<Org> {
  Result getOrgList(Org org);
}
