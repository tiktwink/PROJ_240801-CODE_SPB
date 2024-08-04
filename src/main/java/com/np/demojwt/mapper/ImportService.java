package com.np.demojwt.mapper;

import com.mybatisflex.core.service.IService;
import com.np.demojwt.entity.Import;

import java.util.List;

public interface ImportService extends IService<Import> {
 boolean addImport(Import im);
  
  List<Import> listLog(Import ex);
}
