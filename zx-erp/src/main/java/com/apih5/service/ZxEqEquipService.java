package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquip;

public interface ZxEqEquipService {

    public ResponseEntity getZxEqEquipListByCondition(ZxEqEquip zxEqEquip);

    public ResponseEntity getZxEqEquipDetails(ZxEqEquip zxEqEquip);

    public ResponseEntity saveZxEqEquip(ZxEqEquip zxEqEquip);

    public ResponseEntity updateZxEqEquip(ZxEqEquip zxEqEquip);

    public ResponseEntity batchDeleteUpdateZxEqEquip(List<ZxEqEquip> zxEqEquipList);

	public List<ZxEqEquip> ureportZxEqEquipList(ZxEqEquip zxEqEquip);

	public List<ZxEqEquip> ureportZxEqEquipListIdle(ZxEqEquip zxEqEquip);

    public List<ZxEqEquip> ureportZxEqEquipListVehicle(ZxEqEquip zxEqEquip);

    public List<ZxEqEquip> ureportZxEqEquipListMechanics(ZxEqEquip zxEqEquip);

	public ResponseEntity getZxEqEquipListForRealFact(ZxEqEquip zxEqEquip);

    public ResponseEntity ureportZxEqEquipListMechanicsIdle(ZxEqEquip zxEqEquip);
    
    public ResponseEntity ureportZxEqEquipListVehicleIdle(ZxEqEquip zxEqEquip);

	public ResponseEntity getZxEqEquipListForDepreciation(ZxEqEquip zxEqEquip);

	public ResponseEntity unusedZxEqEquip(ZxEqEquip zxEqEquip);

	public ResponseEntity recoverZxEqEquip(ZxEqEquip zxEqEquip);

	public ResponseEntity getZxEqEquipListForMoveUseOrg(ZxEqEquip zxEqEquip);

	public ResponseEntity getZxEqEquipListForDepreciationRemove(ZxEqEquip zxEqEquip);

	public ResponseEntity getAddZxEqEquipTotalList(ZxEqEquip zxEqEquip);

	public List<ZxEqEquip> ureportAddZxEqEquipTotalList(ZxEqEquip zxEqEquip);

	public ResponseEntity getZxEqEquipListForReport(ZxEqEquip zxEqEquip);
}

