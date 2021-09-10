package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkLimitPriceRep;

public interface ZxSkLimitPriceRepService {

    public ResponseEntity getZxSkLimitPriceRepListByCondition(ZxSkLimitPriceRep zxSkLimitPriceRep);

    public ResponseEntity getZxSkLimitPriceRepDetail(ZxSkLimitPriceRep zxSkLimitPriceRep);

    public ResponseEntity saveZxSkLimitPriceRep(ZxSkLimitPriceRep zxSkLimitPriceRep);

    public ResponseEntity updateZxSkLimitPriceRep(ZxSkLimitPriceRep zxSkLimitPriceRep);

    public ResponseEntity batchDeleteUpdateZxSkLimitPriceRep(List<ZxSkLimitPriceRep> zxSkLimitPriceRepList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkLimitPriceRep> ureportZxSkLimitPriceRep(ZxSkLimitPriceRep zxSkLimitPriceRep);
    
    public ResponseEntity ureportZxSkLimitPriceRepIdle(ZxSkLimitPriceRep zxSkLimitPriceRep);
}
