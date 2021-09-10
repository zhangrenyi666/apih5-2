package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtEqContrAlterItemResoure;

public interface ZxCtEqContrAlterItemResoureService {

    public ResponseEntity getZxCtEqContrAlterItemResoureListByCondition(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure);

    public ResponseEntity getZxCtEqContrAlterItemResoureDetail(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure);

    public ResponseEntity saveZxCtEqContrAlterItemResoure(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure);

    public ResponseEntity updateZxCtEqContrAlterItemResoure(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure);

    public ResponseEntity batchDeleteUpdateZxCtEqContrAlterItemResoure(List<ZxCtEqContrAlterItemResoure> zxCtEqContrAlterItemResoureList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
