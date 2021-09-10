package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSafetyQualityCheckBulletin;

public interface ZjTzSafetyQualityCheckBulletinService {

    public ResponseEntity getZjTzSafetyQualityCheckBulletinListByCondition(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin);

    public ResponseEntity getZjTzSafetyQualityCheckBulletinDetails(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin);

    public ResponseEntity saveZjTzSafetyQualityCheckBulletin(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin);
    
    public ResponseEntity saveZjTzSafetyQualityCheckBulletinAddFile(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin);

    public ResponseEntity updateZjTzSafetyQualityCheckBulletin(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin);
    
    public ResponseEntity updateZjTzSafetyQualityCheckBulletinAddFile(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin);

    public ResponseEntity batchDeleteUpdateZjTzSafetyQualityCheckBulletin(List<ZjTzSafetyQualityCheckBulletin> zjTzSafetyQualityCheckBulletinList);

}

