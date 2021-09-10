package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrCustomerInfoDire;

public interface ZxCrCustomerInfoDireService {

    public ResponseEntity getZxCrCustomerInfoDireListByCondition(ZxCrCustomerInfoDire zxCrCustomerInfoDire);

    public ResponseEntity getZxCrCustomerInfoDireDetail(ZxCrCustomerInfoDire zxCrCustomerInfoDire);

    public ResponseEntity saveZxCrCustomerInfoDire(ZxCrCustomerInfoDire zxCrCustomerInfoDire);

    public ResponseEntity updateZxCrCustomerInfoDire(ZxCrCustomerInfoDire zxCrCustomerInfoDire);

    public ResponseEntity batchDeleteUpdateZxCrCustomerInfoDire(List<ZxCrCustomerInfoDire> zxCrCustomerInfoDireList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
