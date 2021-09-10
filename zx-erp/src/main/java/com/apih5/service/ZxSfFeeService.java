package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfCost;
import com.apih5.mybatis.pojo.ZxSfFee;

public interface ZxSfFeeService {

    public ResponseEntity getZxSfFeeListByCondition(ZxSfFee zxSfFee);

    public ResponseEntity getZxSfFeeDetail(ZxSfFee zxSfFee);

    public ResponseEntity saveZxSfFee(ZxSfFee zxSfFee);

    public ResponseEntity updateZxSfFee(ZxSfFee zxSfFee);

    public ResponseEntity batchDeleteUpdateZxSfFee(List<ZxSfFee> zxSfFeeList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getCompany(ZxSfFee zxSfCost);

    public ResponseEntity getCompanyList(ZxSfFee zxSfCost);

    public ResponseEntity getOrgCostList(ZxSfFee zxSfCost);

    public ResponseEntity getGuiDangList(ZxSfFee zxSfCost);

    public ResponseEntity getJiaoGongList(ZxSfFee zxSfCost);

    public ResponseEntity getWanGongList(ZxSfFee zxSfCost);

    public ResponseEntity getKaiGongList(ZxSfFee zxSfCost);

    public ResponseEntity getJuInfo(ZxSfFee zxSfCost);
}
