package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqUseEquipIn;

public interface ZxEqUseEquipInService {

    public ResponseEntity getZxEqUseEquipInListByCondition(ZxEqUseEquipIn zxEqUseEquipIn);

    public ResponseEntity getZxEqUseEquipInDetails(ZxEqUseEquipIn zxEqUseEquipIn);

    public ResponseEntity saveZxEqUseEquipIn(ZxEqUseEquipIn zxEqUseEquipIn);

    public ResponseEntity updateZxEqUseEquipIn(ZxEqUseEquipIn zxEqUseEquipIn);

    public ResponseEntity batchDeleteUpdateZxEqUseEquipIn(List<ZxEqUseEquipIn> zxEqUseEquipInList);

}

