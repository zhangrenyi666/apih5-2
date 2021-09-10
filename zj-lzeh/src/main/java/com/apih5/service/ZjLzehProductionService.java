package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehProduction;

public interface ZjLzehProductionService {

    public ResponseEntity getZjLzehProductionListByCondition(ZjLzehProduction zjLzehProduction);

    public ResponseEntity getZjLzehProductionDetails(ZjLzehProduction zjLzehProduction);

    public ResponseEntity saveZjLzehProduction(ZjLzehProduction zjLzehProduction);

    public ResponseEntity updateZjLzehProduction(ZjLzehProduction zjLzehProduction);

    public ResponseEntity batchDeleteUpdateZjLzehProduction(List<ZjLzehProduction> zjLzehProductionList);
    
    public ResponseEntity updateZjLzehProductionByProjectId(ZjLzehProduction zjLzehProduction);
}

