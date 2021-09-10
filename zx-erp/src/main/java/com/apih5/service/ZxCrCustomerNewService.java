package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrCustomerNew;

public interface ZxCrCustomerNewService {

    public ResponseEntity getZxCrCustomerNewListByCondition(ZxCrCustomerNew zxCrCustomerNew);

    public ResponseEntity getZxCrCustomerNewDetail(ZxCrCustomerNew zxCrCustomerNew);

    public ResponseEntity saveZxCrCustomerNew(ZxCrCustomerNew zxCrCustomerNew);

    public ResponseEntity updateZxCrCustomerNew(ZxCrCustomerNew zxCrCustomerNew);

    public ResponseEntity batchDeleteUpdateZxCrCustomerNew(List<ZxCrCustomerNew> zxCrCustomerNewList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
