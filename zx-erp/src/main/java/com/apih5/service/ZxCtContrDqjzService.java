package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrDqjz;

public interface ZxCtContrDqjzService {

    public ResponseEntity getZxCtContrDqjzListByCondition(ZxCtContrDqjz zxCtContrDqjz);

    public ResponseEntity getZxCtContrDqjzDetail(ZxCtContrDqjz zxCtContrDqjz);

    public ResponseEntity saveZxCtContrDqjz(ZxCtContrDqjz zxCtContrDqjz);

    public ResponseEntity updateZxCtContrDqjz(ZxCtContrDqjz zxCtContrDqjz);

    public ResponseEntity batchDeleteUpdateZxCtContrDqjz(List<ZxCtContrDqjz> zxCtContrDqjzList);

    public ResponseEntity getZxCtContrDqjzItemList(ZxCtContrDqjz zxCtContrDqjz);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
