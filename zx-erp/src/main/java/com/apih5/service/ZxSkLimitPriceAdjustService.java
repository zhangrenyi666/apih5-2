package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkLimitPriceAdjust;

public interface ZxSkLimitPriceAdjustService {

    public ResponseEntity getZxSkLimitPriceAdjustListByCondition(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust);

    public ResponseEntity getZxSkLimitPriceAdjustDetails(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust);

    public ResponseEntity saveZxSkLimitPriceAdjust(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust);

    public ResponseEntity updateZxSkLimitPriceAdjust(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust);

    public ResponseEntity batchDeleteUpdateZxSkLimitPriceAdjust(List<ZxSkLimitPriceAdjust> zxSkLimitPriceAdjustList);

    public ResponseEntity getZxSkLimitPriceAdjustNo(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust);


}

