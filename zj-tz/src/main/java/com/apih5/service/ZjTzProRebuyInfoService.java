package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProRebuyInfo;

public interface ZjTzProRebuyInfoService {

    public ResponseEntity getZjTzProRebuyInfoListByCondition(ZjTzProRebuyInfo zjTzProRebuyInfo);

    public ResponseEntity getZjTzProRebuyInfoDetails(ZjTzProRebuyInfo zjTzProRebuyInfo);

    public ResponseEntity saveZjTzProRebuyInfo(ZjTzProRebuyInfo zjTzProRebuyInfo);

    public ResponseEntity updateZjTzProRebuyInfo(ZjTzProRebuyInfo zjTzProRebuyInfo);

    public ResponseEntity batchDeleteUpdateZjTzProRebuyInfo(List<ZjTzProRebuyInfo> zjTzProRebuyInfoList);

}

