package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaOtherEquipSettle;

import javax.servlet.http.HttpServletResponse;

public interface ZxSaOtherEquipSettleService {

    public ResponseEntity getZxSaOtherEquipSettleListByCondition(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public ResponseEntity getZxSaOtherEquipSettleDetail(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public ResponseEntity saveZxSaOtherEquipSettle(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public ResponseEntity updateZxSaOtherEquipSettle(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public ResponseEntity batchDeleteUpdateZxSaOtherEquipSettle(List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSaOtherEquipSettleContractNo(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public ResponseEntity zxSaOtherEquipSettleReviewApply(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public ResponseEntity getZxSaOtherEquipSettleBillNo(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public void exportZxSaOtherEquipSettle(ZxSaOtherEquipSettle zxSaOtherEquipSettle, HttpServletResponse response);

    public List<ZxSaOtherEquipSettle> ureportZxSaOtherEquipSettleList(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public ResponseEntity getUreportZxSaOtherEquipSettleList(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public List<ZxSaOtherEquipSettle> ureportZxSaOtherEquipSettleList_YGZ(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public ResponseEntity getIsFirstByContractId(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    public ResponseEntity getSettleTypeByContractId(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

}
