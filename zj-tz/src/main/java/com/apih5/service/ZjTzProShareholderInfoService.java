package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProShareholderInfo;

public interface ZjTzProShareholderInfoService {

    public ResponseEntity getZjTzProShareholderInfoListByCondition(ZjTzProShareholderInfo zjTzProShareholderInfo);

    public ResponseEntity getZjTzProShareholderInfoDetails(ZjTzProShareholderInfo zjTzProShareholderInfo);

    public ResponseEntity saveZjTzProShareholderInfo(ZjTzProShareholderInfo zjTzProShareholderInfo);

    public ResponseEntity updateZjTzProShareholderInfo(ZjTzProShareholderInfo zjTzProShareholderInfo);

    public ResponseEntity batchDeleteUpdateZjTzProShareholderInfo(List<ZjTzProShareholderInfo> zjTzProShareholderInfoList);

}

