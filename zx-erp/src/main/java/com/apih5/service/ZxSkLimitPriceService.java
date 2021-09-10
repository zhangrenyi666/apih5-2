package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkLimitPrice;

public interface ZxSkLimitPriceService {

    public ResponseEntity getZxSkLimitPriceListByCondition(ZxSkLimitPrice zxSkLimitPrice);

    public ResponseEntity getZxSkLimitPriceDetails(ZxSkLimitPrice zxSkLimitPrice);

    public ResponseEntity saveZxSkLimitPrice(ZxSkLimitPrice zxSkLimitPrice);

    public ResponseEntity updateZxSkLimitPrice(ZxSkLimitPrice zxSkLimitPrice);

    public ResponseEntity batchDeleteUpdateZxSkLimitPrice(List<ZxSkLimitPrice> zxSkLimitPriceList);

    public ResponseEntity getZxSkLimitPriceBase(ZxSkLimitPrice zxSkLimitPrice);

    public ResponseEntity getZxSkLimitPriceByLimitNoList(ZxSkLimitPrice zxSkLimitPrice);

}

