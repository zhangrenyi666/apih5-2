package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrCustomerInfo;

public interface ZxCrCustomerInfoService {

    public ResponseEntity getZxCrCustomerInfoListByCondition(ZxCrCustomerInfo zxCrCustomerInfo);

    public ResponseEntity getZxCrCustomerInfoDetail(ZxCrCustomerInfo zxCrCustomerInfo);

    public ResponseEntity saveZxCrCustomerInfo(ZxCrCustomerInfo zxCrCustomerInfo);

    public ResponseEntity updateZxCrCustomerInfo(ZxCrCustomerInfo zxCrCustomerInfo);

    public ResponseEntity batchDeleteUpdateZxCrCustomerInfo(List<ZxCrCustomerInfo> zxCrCustomerInfoList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity getZxCrCustomerInfoListOne(ZxCrCustomerInfo zxCrCustomerInfo);
}
