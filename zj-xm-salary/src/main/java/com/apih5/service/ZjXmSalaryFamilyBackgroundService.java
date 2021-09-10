package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryFamilyBackground;

public interface ZjXmSalaryFamilyBackgroundService {

    public ResponseEntity getZjXmSalaryFamilyBackgroundListByCondition(ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground);

    public ResponseEntity getZjXmSalaryFamilyBackgroundDetails(ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground);

    public ResponseEntity saveZjXmSalaryFamilyBackground(ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground);

    public ResponseEntity updateZjXmSalaryFamilyBackground(ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground);

    public ResponseEntity batchDeleteUpdateZjXmSalaryFamilyBackground(List<ZjXmSalaryFamilyBackground> zjXmSalaryFamilyBackgroundList);

}

