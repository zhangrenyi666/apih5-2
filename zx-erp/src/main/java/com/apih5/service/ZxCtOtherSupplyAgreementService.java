package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtOtherSupplyAgreement;

import javax.servlet.http.HttpServletResponse;

public interface ZxCtOtherSupplyAgreementService {

    public ResponseEntity getZxCtOtherSupplyAgreementListByCondition(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement);

    public ResponseEntity getZxCtOtherSupplyAgreementDetail(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement);

    public ResponseEntity saveZxCtOtherSupplyAgreement(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement);

    public ResponseEntity updateZxCtOtherSupplyAgreement(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement);

    public ResponseEntity batchDeleteUpdateZxCtOtherSupplyAgreement(List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity zxCtOtherSupplyAgreementReviewApply(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement);

    public ResponseEntity getZxCtOtherSupplyAgreementContractNo(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement);

    public void exportZxCtOtherSupplyAgreement(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement, HttpServletResponse response);

    public ResponseEntity zxCtOtherSupplyAgreementReviewApplyCheck(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement);
}
