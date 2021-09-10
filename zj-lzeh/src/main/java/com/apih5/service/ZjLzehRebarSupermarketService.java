package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehRebarSupermarket;

public interface ZjLzehRebarSupermarketService {

    public ResponseEntity getZjLzehRebarSupermarketListByCondition(ZjLzehRebarSupermarket zjLzehRebarSupermarket);

    public ResponseEntity getZjLzehRebarSupermarketDetails(ZjLzehRebarSupermarket zjLzehRebarSupermarket);

    public ResponseEntity saveZjLzehRebarSupermarket(ZjLzehRebarSupermarket zjLzehRebarSupermarket);

    public ResponseEntity updateZjLzehRebarSupermarket(ZjLzehRebarSupermarket zjLzehRebarSupermarket);

    public ResponseEntity batchDeleteUpdateZjLzehRebarSupermarket(List<ZjLzehRebarSupermarket> zjLzehRebarSupermarketList);
    
    public ResponseEntity updateZjLzehRebarSupermarketByProjectId(ZjLzehRebarSupermarket zjLzehRebarSupermarket);
    
    public ResponseEntity getZjLzehPageDataForView(ZjLzehRebarSupermarket zjLzehRebarSupermarket);
    
}

