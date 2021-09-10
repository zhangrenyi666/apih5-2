package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrClaim;

public interface ZxCtContrClaimService {

    public ResponseEntity getZxCtContrClaimListByCondition(ZxCtContrClaim zxCtContrClaim);

    public ResponseEntity getZxCtContrClaimDetail(ZxCtContrClaim zxCtContrClaim);

    public ResponseEntity saveZxCtContrClaim(ZxCtContrClaim zxCtContrClaim);

    public ResponseEntity updateZxCtContrClaim(ZxCtContrClaim zxCtContrClaim);

    public ResponseEntity batchDeleteUpdateZxCtContrClaim(List<ZxCtContrClaim> zxCtContrClaimList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
