package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrCustomerExtAttr;

public interface ZxCrCustomerExtAttrService {

    public ResponseEntity getZxCrCustomerExtAttrListByCondition(ZxCrCustomerExtAttr zxCrCustomerExtAttr);

    public ResponseEntity getZxCrCustomerExtAttrDetail(ZxCrCustomerExtAttr zxCrCustomerExtAttr);

    public ResponseEntity saveZxCrCustomerExtAttr(ZxCrCustomerExtAttr zxCrCustomerExtAttr);

    public ResponseEntity updateZxCrCustomerExtAttr(ZxCrCustomerExtAttr zxCrCustomerExtAttr);

    public ResponseEntity batchDeleteUpdateZxCrCustomerExtAttr(List<ZxCrCustomerExtAttr> zxCrCustomerExtAttrList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity getZxCrCustomerExtAttrDetailRetrieval(ZxCrCustomerExtAttr zxCrCustomerExtAttr);
    
    public ResponseEntity getZxCrCustomerExtAttrAll(ZxCrCustomerExtAttr zxCrCustomerExtAttr);
    
    public ResponseEntity getZxCrCustomerExtAttrListAll(ZxCrCustomerExtAttr zxCrCustomerExtAttr);

    public ResponseEntity addZxCrCustomerExtAttrPicking(ZxCrCustomerExtAttr zxCrCustomerExtAttr);

    public ResponseEntity updateZxCrCustomerExtAttrPicking(ZxCrCustomerExtAttr zxCrCustomerExtAttr);

    public ResponseEntity getZxCrCustomerExtAttrPickingList(ZxCrCustomerExtAttr zxCrCustomerExtAttr);
    
    public ResponseEntity getZxCrCustomerExtAttrEngineeringList(ZxCrCustomerExtAttr zxCrCustomerExtAttr);
}
