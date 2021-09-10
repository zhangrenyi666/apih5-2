package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;

public interface ZjXmCqjxDepartmentHeadDetailService {

    public ResponseEntity getZjXmCqjxDepartmentHeadDetailListByCondition(ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail);

    public ResponseEntity getZjXmCqjxDepartmentHeadDetailDetails(ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail);

    public ResponseEntity saveZjXmCqjxDepartmentHeadDetail(ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail);

    public ResponseEntity updateZjXmCqjxDepartmentHeadDetail(ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail);

    public ResponseEntity batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(List<ZjXmCqjxDepartmentHeadDetail> zjXmCqjxDepartmentHeadDetailList);

}

