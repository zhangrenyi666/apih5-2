package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquipLimitPrice;

public interface ZxEqEquipLimitPriceService {

    public ResponseEntity getZxEqEquipLimitPriceListByCondition(ZxEqEquipLimitPrice zxEqEquipLimitPrice);

    public ResponseEntity getZxEqEquipLimitPriceDetails(ZxEqEquipLimitPrice zxEqEquipLimitPrice);

    public ResponseEntity saveZxEqEquipLimitPrice(ZxEqEquipLimitPrice zxEqEquipLimitPrice);

    public ResponseEntity updateZxEqEquipLimitPrice(ZxEqEquipLimitPrice zxEqEquipLimitPrice);

    public ResponseEntity batchDeleteUpdateZxEqEquipLimitPrice(List<ZxEqEquipLimitPrice> zxEqEquipLimitPriceList);

	public ResponseEntity copyZxEqEquipLimitPrice(ZxEqEquipLimitPrice zxEqEquipLimitPrice);

	public ResponseEntity getZxEqEquipLimitPriceApplyNo(ZxEqEquipLimitPrice zxEqEquipLimitPrice);

	public ResponseEntity ureportZxEqEquipLimitPriceVOIdle(ZxEqEquipLimitPrice zxEqEquipLimitPrice);

	public List<ZxEqEquipLimitPrice> ureportZxEqEquipLimitPriceVO(ZxEqEquipLimitPrice zxEqEquipLimitPrice);

}

