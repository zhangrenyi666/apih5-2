package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquipDepreciation;

public interface ZxEqEquipDepreciationService {

    public ResponseEntity getZxEqEquipDepreciationListByCondition(ZxEqEquipDepreciation zxEqEquipDepreciation);

    public ResponseEntity getZxEqEquipDepreciationDetails(ZxEqEquipDepreciation zxEqEquipDepreciation);

    public ResponseEntity saveZxEqEquipDepreciation(ZxEqEquipDepreciation zxEqEquipDepreciation);

    public ResponseEntity updateZxEqEquipDepreciation(ZxEqEquipDepreciation zxEqEquipDepreciation);

    public ResponseEntity batchDeleteUpdateZxEqEquipDepreciation(List<ZxEqEquipDepreciation> zxEqEquipDepreciationList);

	public ResponseEntity auditZxEqEquipDepreciation(ZxEqEquipDepreciation zxEqEquipDepreciation);

}

