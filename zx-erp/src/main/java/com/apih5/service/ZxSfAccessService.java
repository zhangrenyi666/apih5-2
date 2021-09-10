package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfAccess;

public interface ZxSfAccessService {

    public ResponseEntity getZxSfAccessListByCondition(ZxSfAccess zxSfAccess);

    public ResponseEntity getZxSfAccessDetail(ZxSfAccess zxSfAccess);

    public ResponseEntity saveZxSfAccess(ZxSfAccess zxSfAccess);

    public ResponseEntity updateZxSfAccess(ZxSfAccess zxSfAccess);

    public ResponseEntity batchDeleteUpdateZxSfAccess(List<ZxSfAccess> zxSfAccessList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity updateZxSfAccessOutDate(ZxSfAccess zxSfAccess);
    
    public ResponseEntity updateZxSfAccessOutDateReturn(ZxSfAccess zxSfAccess);
}
