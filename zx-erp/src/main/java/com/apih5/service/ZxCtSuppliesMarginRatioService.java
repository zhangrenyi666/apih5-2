package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesMarginRatio;

public interface ZxCtSuppliesMarginRatioService {

    public ResponseEntity getZxCtSuppliesMarginRatioListByCondition(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio);

    public ResponseEntity getZxCtSuppliesMarginRatioDetail(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio);

    public ResponseEntity saveZxCtSuppliesMarginRatio(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio);

    public ResponseEntity updateZxCtSuppliesMarginRatio(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesMarginRatio(List<ZxCtSuppliesMarginRatio> zxCtSuppliesMarginRatioList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity saveZxCtSuppliesMarginRatioByConID(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio);
    
    public ResponseEntity updateZxCtSuppliesMarginRatioByConID(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio);
}
