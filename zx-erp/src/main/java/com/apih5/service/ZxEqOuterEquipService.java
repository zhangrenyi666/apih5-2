package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqOuterEquip;

public interface ZxEqOuterEquipService {

    public ResponseEntity getZxEqOuterEquipListByCondition(ZxEqOuterEquip zxEqOuterEquip);

    public ResponseEntity getZxEqOuterEquipDetails(ZxEqOuterEquip zxEqOuterEquip);

    public ResponseEntity saveZxEqOuterEquip(ZxEqOuterEquip zxEqOuterEquip);

    public ResponseEntity updateZxEqOuterEquip(ZxEqOuterEquip zxEqOuterEquip);

    public ResponseEntity batchDeleteUpdateZxEqOuterEquip(List<ZxEqOuterEquip> zxEqOuterEquipList);

    public List<ZxEqOuterEquip> reportZuLinCLForm(ZxEqOuterEquip zxEqOuterEquip);

    public ResponseEntity getZxEqOuterEquipListForCar(ZxEqOuterEquip zxEqOuterEquip);

	public ResponseEntity getZxEqOuterEquipListForRecord(ZxEqOuterEquip zxEqOuterEquip);

}

