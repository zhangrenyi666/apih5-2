package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysDepartmentPermission;

public interface SysDepartmentPermissionService {

    public ResponseEntity getSysDepartmentPermissionListByCondition(SysDepartmentPermission sysDepartmentPermission);

    public ResponseEntity saveSysDepartmentPermission(SysDepartmentPermission sysDepartmentPermission);

    public ResponseEntity updateSysDepartmentPermission(SysDepartmentPermission sysDepartmentPermission);

    public ResponseEntity batchDeleteUpdateSysDepartmentPermission(List<SysDepartmentPermission> sysDepartmentPermissionList);

}

