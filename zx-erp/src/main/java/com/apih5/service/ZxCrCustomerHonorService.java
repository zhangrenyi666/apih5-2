package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrCustomerHonor;

public interface ZxCrCustomerHonorService {

    public ResponseEntity getZxCrCustomerHonorListByCondition(ZxCrCustomerHonor zxCrCustomerHonor);

    public ResponseEntity getZxCrCustomerHonorDetail(ZxCrCustomerHonor zxCrCustomerHonor);

    public ResponseEntity saveZxCrCustomerHonor(ZxCrCustomerHonor zxCrCustomerHonor);

    public ResponseEntity updateZxCrCustomerHonor(ZxCrCustomerHonor zxCrCustomerHonor);

    public ResponseEntity batchDeleteUpdateZxCrCustomerHonor(List<ZxCrCustomerHonor> zxCrCustomerHonorList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
