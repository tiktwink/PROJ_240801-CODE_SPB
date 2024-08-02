package com.np.demojwt.mapper;

import com.mybatisflex.core.service.IService;
import com.np.demojwt.entity.Import;

public interface ImportService extends IService<Import> {
  Integer addImport(Import im);
}
