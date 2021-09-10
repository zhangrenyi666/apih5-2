package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfCost;

public interface ZxSfCostService {

    public ResponseEntity getZxSfCostListByCondition(ZxSfCost zxSfCost);

    public ResponseEntity getZxSfCostDetail(ZxSfCost zxSfCost);

    public ResponseEntity saveZxSfCost(ZxSfCost zxSfCost);

    public ResponseEntity updateZxSfCost(ZxSfCost zxSfCost);

    public ResponseEntity batchDeleteUpdateZxSfCost(List<ZxSfCost> zxSfCostList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getCompany(ZxSfCost zxSfCost);

    public ResponseEntity getCompanyList(ZxSfCost zxSfCost);

    public ResponseEntity getOrgCostList(ZxSfCost zxSfCost);

    public ResponseEntity getGuiDangList(ZxSfCost zxSfCost);

    public ResponseEntity getJiaoGongList(ZxSfCost zxSfCost);

    public ResponseEntity getWanGongList(ZxSfCost zxSfCost);

    public ResponseEntity getKaiGongList(ZxSfCost zxSfCost);


}
