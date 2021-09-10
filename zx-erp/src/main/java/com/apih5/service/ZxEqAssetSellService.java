package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqAssetSell;

public interface ZxEqAssetSellService {

    public ResponseEntity getZxEqAssetSellListByCondition(ZxEqAssetSell zxEqAssetSell);

    public ResponseEntity getZxEqAssetSellDetails(ZxEqAssetSell zxEqAssetSell);

    public ResponseEntity saveZxEqAssetSell(ZxEqAssetSell zxEqAssetSell);

    public ResponseEntity updateZxEqAssetSell(ZxEqAssetSell zxEqAssetSell);

    public ResponseEntity batchDeleteUpdateZxEqAssetSell(List<ZxEqAssetSell> zxEqAssetSellList);

	public ResponseEntity reportZxEqAssetSell(ZxEqAssetSell zxEqAssetSell);

	public ResponseEntity auditAgreeZxEqAssetSell(ZxEqAssetSell zxEqAssetSell);

	public ResponseEntity auditRefuseZxEqAssetSell(ZxEqAssetSell zxEqAssetSell);

}

