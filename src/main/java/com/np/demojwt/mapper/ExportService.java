package com.np.demojwt.mapper;

import com.mybatisflex.core.service.IService;
import com.np.demojwt.entity.Export;

import java.util.List;

public interface ExportService extends IService<Export> {
  boolean addExport(Export ex);
  
  List<Export> listLog(Export ex);
}
