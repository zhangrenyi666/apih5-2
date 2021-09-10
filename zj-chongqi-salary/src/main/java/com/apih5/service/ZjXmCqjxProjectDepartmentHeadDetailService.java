package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHeadDetail;

public interface ZjXmCqjxProjectDepartmentHeadDetailService {

    public ResponseEntity getZjXmCqjxProjectDepartmentHeadDetailListByCondition(ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail);

    public ResponseEntity getZjXmCqjxProjectDepartmentHeadDetailDetails(ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail);

    public ResponseEntity saveZjXmCqjxProjectDepartmentHeadDetail(ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail);

    public ResponseEntity updateZjXmCqjxProjectDepartmentHeadDetail(ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(List<ZjXmCqjxProjectDepartmentHeadDetail> zjXmCqjxProjectDepartmentHeadDetailList);

}

