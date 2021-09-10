package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqCooEquip;

public interface ZxEqCooEquipService {

    public ResponseEntity getZxEqCooEquipListByCondition(ZxEqCooEquip zxEqCooEquip);

    public ResponseEntity getZxEqCooEquipDetails(ZxEqCooEquip zxEqCooEquip);

    public ResponseEntity saveZxEqCooEquip(ZxEqCooEquip zxEqCooEquip);

    public ResponseEntity updateZxEqCooEquip(ZxEqCooEquip zxEqCooEquip);

    public ResponseEntity batchDeleteUpdateZxEqCooEquip(List<ZxEqCooEquip> zxEqCooEquipList);

	public ResponseEntity importZxEqCooEquipList(ZxEqCooEquip zxEqCooEquip);

}

