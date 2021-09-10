package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;

public interface ZjTzProSubprojectInfoService {

    public ResponseEntity getZjTzProSubprojectInfoListByCondition(ZjTzProSubprojectInfo zjTzProSubprojectInfo);

    public ResponseEntity getZjTzProSubprojectInfoDetails(ZjTzProSubprojectInfo zjTzProSubprojectInfo);

    public ResponseEntity saveZjTzProSubprojectInfo(ZjTzProSubprojectInfo zjTzProSubprojectInfo);

    public ResponseEntity updateZjTzProSubprojectInfo(ZjTzProSubprojectInfo zjTzProSubprojectInfo);

    public ResponseEntity batchDeleteUpdateZjTzProSubprojectInfo(List<ZjTzProSubprojectInfo> zjTzProSubprojectInfoList);

}

