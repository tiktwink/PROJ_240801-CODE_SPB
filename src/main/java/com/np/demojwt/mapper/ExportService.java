package com.np.demojwt.mapper;

import com.mybatisflex.core.service.IService;
import com.np.demojwt.entity.Export;

public interface ExportService extends IService<Export> {
  Integer addExport(Export ex);
}
