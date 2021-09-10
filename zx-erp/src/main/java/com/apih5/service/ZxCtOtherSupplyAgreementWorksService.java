package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtOtherSupplyAgreementWorks;

public interface ZxCtOtherSupplyAgreementWorksService {

    public ResponseEntity getZxCtOtherSupplyAgreementWorksListByCondition(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks);

    public ResponseEntity getZxCtOtherSupplyAgreementWorksDetail(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks);

    public ResponseEntity saveZxCtOtherSupplyAgreementWorks(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks);

    public ResponseEntity updateZxCtOtherSupplyAgreementWorks(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks);

    public ResponseEntity batchDeleteUpdateZxCtOtherSupplyAgreementWorks(List<ZxCtOtherSupplyAgreementWorks> zxCtOtherSupplyAgreementWorksList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
