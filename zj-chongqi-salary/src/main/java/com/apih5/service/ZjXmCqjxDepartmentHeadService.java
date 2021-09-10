package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;

public interface ZjXmCqjxDepartmentHeadService {

    public ResponseEntity getZjXmCqjxDepartmentHeadListByCondition(ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead);

    public ResponseEntity getZjXmCqjxDepartmentHeadDetails(ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead);

    public ResponseEntity saveZjXmCqjxDepartmentHead(ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead);

    public ResponseEntity updateZjXmCqjxDepartmentHead(ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead);

    public ResponseEntity batchDeleteUpdateZjXmCqjxDepartmentHead(List<ZjXmCqjxDepartmentHead> zjXmCqjxDepartmentHeadList);

}

