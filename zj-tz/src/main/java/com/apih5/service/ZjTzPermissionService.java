package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPermission;

public interface ZjTzPermissionService {

    public ResponseEntity getZjTzPermissionListByCondition(ZjTzPermission zjTzPermission);

    public ResponseEntity getZjTzPermissionDetails(ZjTzPermission zjTzPermission);

    public ResponseEntity saveZjTzPermission(ZjTzPermission zjTzPermission);

    public ResponseEntity updateZjTzPermission(ZjTzPermission zjTzPermission);

    public ResponseEntity batchDeleteUpdateZjTzPermission(List<ZjTzPermission> zjTzPermissionList);

    public ResponseEntity getZjTzPermissionListByProject(ZjTzPermission zjTzPermission);

    public String getZjtzProjectSql();

    public String getZjtzSysDepartmentIdType(ZjTzPermission zjTzPermission);

	String getZjtzProjectSqlForHome();
}

