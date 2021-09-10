package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtFsSideAgreement;

import javax.servlet.http.HttpServletResponse;

public interface ZxCtFsSideAgreementService {

    public ResponseEntity getZxCtFsSideAgreementListByCondition(ZxCtFsSideAgreement zxCtFsSideAgreement);

    public ResponseEntity getZxCtFsSideAgreementDetail(ZxCtFsSideAgreement zxCtFsSideAgreement);

    public ResponseEntity saveZxCtFsSideAgreement(ZxCtFsSideAgreement zxCtFsSideAgreement);

    public ResponseEntity updateZxCtFsSideAgreement(ZxCtFsSideAgreement zxCtFsSideAgreement)throws Exception;

    public ResponseEntity batchDeleteUpdateZxCtFsSideAgreement(List<ZxCtFsSideAgreement> zxCtFsSideAgreementList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 更新附属合同补充协议并批量更新
     * @author suncg
     * @param zxCtFsSideAgreement
     * */
    public ResponseEntity updateAgreementAndInventoryList(ZxCtFsSideAgreement zxCtFsSideAgreement) throws Exception;

    public ResponseEntity exportSideAgreement(ZxCtFsSideAgreement zxCtFsSideAgreement, HttpServletResponse response);

    /**
     * 获取附属合同补充协议编号
     * @author suncg
     * @param zxCtFsSideAgreement
     * */
    public ResponseEntity getContractNo(ZxCtFsSideAgreement zxCtFsSideAgreement);
}
