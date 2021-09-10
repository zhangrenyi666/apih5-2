package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrCustomerInfoQa;

public interface ZxCrCustomerInfoQaService {

    public ResponseEntity getZxCrCustomerInfoQaListByCondition(ZxCrCustomerInfoQa zxCrCustomerInfoQa);

    public ResponseEntity getZxCrCustomerInfoQaDetail(ZxCrCustomerInfoQa zxCrCustomerInfoQa);

    public ResponseEntity saveZxCrCustomerInfoQa(ZxCrCustomerInfoQa zxCrCustomerInfoQa);

    public ResponseEntity updateZxCrCustomerInfoQa(ZxCrCustomerInfoQa zxCrCustomerInfoQa);

    public ResponseEntity batchDeleteUpdateZxCrCustomerInfoQa(List<ZxCrCustomerInfoQa> zxCrCustomerInfoQaList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
