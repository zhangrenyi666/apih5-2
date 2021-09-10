package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrDetailAttr;

public interface ZxCtContrDetailAttrService {

    public ResponseEntity getZxCtContrDetailAttrListByCondition(ZxCtContrDetailAttr zxCtContrDetailAttr);

    public ResponseEntity getZxCtContrDetailAttrDetail(ZxCtContrDetailAttr zxCtContrDetailAttr);

    public ResponseEntity saveZxCtContrDetailAttr(ZxCtContrDetailAttr zxCtContrDetailAttr);

    public ResponseEntity updateZxCtContrDetailAttr(ZxCtContrDetailAttr zxCtContrDetailAttr);

    public ResponseEntity batchDeleteUpdateZxCtContrDetailAttr(List<ZxCtContrDetailAttr> zxCtContrDetailAttrList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity deleteAllZxCtContrDetailAttr(ZxCtContrDetailAttr zxCtContrDetailAttr);
}
