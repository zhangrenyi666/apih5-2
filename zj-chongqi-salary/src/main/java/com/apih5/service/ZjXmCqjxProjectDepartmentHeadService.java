package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHead;

public interface ZjXmCqjxProjectDepartmentHeadService {

    public ResponseEntity getZjXmCqjxProjectDepartmentHeadListByCondition(ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead);

    public ResponseEntity getZjXmCqjxProjectDepartmentHeadDetails(ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead);

    public ResponseEntity saveZjXmCqjxProjectDepartmentHead(ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead);

    public ResponseEntity updateZjXmCqjxProjectDepartmentHead(ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDepartmentHead(List<ZjXmCqjxProjectDepartmentHead> zjXmCqjxProjectDepartmentHeadList);

}

