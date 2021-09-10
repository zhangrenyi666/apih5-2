package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquipFact;

public interface ZxEqEquipFactService {

    public ResponseEntity getZxEqEquipFactListByCondition(ZxEqEquipFact zxEqEquipFact);

    public ResponseEntity getZxEqEquipFactDetails(ZxEqEquipFact zxEqEquipFact);

    public ResponseEntity saveZxEqEquipFact(ZxEqEquipFact zxEqEquipFact);

    public ResponseEntity updateZxEqEquipFact(ZxEqEquipFact zxEqEquipFact);

    public ResponseEntity batchDeleteUpdateZxEqEquipFact(List<ZxEqEquipFact> zxEqEquipFactList);

	public ResponseEntity getZxEqEquipFactListForCopy(ZxEqEquipFact zxEqEquipFact);

	public ResponseEntity auditZxEqEquipFact(ZxEqEquipFact zxEqEquipFact);

}

