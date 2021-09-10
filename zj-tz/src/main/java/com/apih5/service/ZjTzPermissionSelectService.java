package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPermissionSelect;

public interface ZjTzPermissionSelectService {

    public ResponseEntity getZjTzPermissionSelectListByCondition(ZjTzPermissionSelect zjTzPermissionSelect);

    public ResponseEntity getZjTzPermissionSelectDetails(ZjTzPermissionSelect zjTzPermissionSelect);

    public ResponseEntity saveZjTzPermissionSelect(ZjTzPermissionSelect zjTzPermissionSelect);

    public ResponseEntity updateZjTzPermissionSelect(ZjTzPermissionSelect zjTzPermissionSelect);

    public ResponseEntity batchDeleteUpdateZjTzPermissionSelect(List<ZjTzPermissionSelect> zjTzPermissionSelectList);

    public ResponseEntity changeZjTzProjectManagement(ZjTzPermissionSelect zjTzPermissionSelect);
}

