package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqUseEquip;

public interface ZxEqUseEquipService {

    public ResponseEntity getZxEqUseEquipListByCondition(ZxEqUseEquip zxEqUseEquip);

    public ResponseEntity getZxEqUseEquipDetails(ZxEqUseEquip zxEqUseEquip);

    public ResponseEntity saveZxEqUseEquip(ZxEqUseEquip zxEqUseEquip);

    public ResponseEntity updateZxEqUseEquip(ZxEqUseEquip zxEqUseEquip);

    public ResponseEntity batchDeleteUpdateZxEqUseEquip(List<ZxEqUseEquip> zxEqUseEquipList);

}

