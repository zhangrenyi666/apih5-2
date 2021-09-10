package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquipFactReal;

public interface ZxEqEquipFactRealService {

    public ResponseEntity getZxEqEquipFactRealListByCondition(ZxEqEquipFactReal zxEqEquipFactReal);

    public ResponseEntity getZxEqEquipFactRealDetails(ZxEqEquipFactReal zxEqEquipFactReal);

    public ResponseEntity saveZxEqEquipFactReal(ZxEqEquipFactReal zxEqEquipFactReal);

    public ResponseEntity updateZxEqEquipFactReal(ZxEqEquipFactReal zxEqEquipFactReal);

    public ResponseEntity batchDeleteUpdateZxEqEquipFactReal(List<ZxEqEquipFactReal> zxEqEquipFactRealList);

	public ResponseEntity syncZxEqEquipFactReal(ZxEqEquipFactReal zxEqEquipFactReal);

	public ResponseEntity auditZxEqEquipFactReal(ZxEqEquipFactReal zxEqEquipFactReal);

}

