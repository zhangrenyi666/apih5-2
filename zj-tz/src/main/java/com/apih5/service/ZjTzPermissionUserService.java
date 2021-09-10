package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPermissionUser;

public interface ZjTzPermissionUserService {

    public ResponseEntity getZjTzPermissionUserListByCondition(ZjTzPermissionUser zjTzPermissionUser);

    public ResponseEntity getZjTzPermissionUserDetails(ZjTzPermissionUser zjTzPermissionUser);

    public ResponseEntity saveZjTzPermissionUser(ZjTzPermissionUser zjTzPermissionUser);

    public ResponseEntity updateZjTzPermissionUser(ZjTzPermissionUser zjTzPermissionUser);

    public ResponseEntity batchDeleteUpdateZjTzPermissionUser(List<ZjTzPermissionUser> zjTzPermissionUserList);

}

