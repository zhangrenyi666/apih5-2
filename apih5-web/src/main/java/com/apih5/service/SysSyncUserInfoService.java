package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysSyncUserInfo;

import cn.hutool.json.JSONArray;

public interface SysSyncUserInfoService {

    public ResponseEntity getSysSyncUserInfoListByCondition(SysSyncUserInfo sysSyncUserInfo);

    public ResponseEntity getSysSyncUserInfoDetails(SysSyncUserInfo sysSyncUserInfo);

    public ResponseEntity saveSysSyncUserInfo(SysSyncUserInfo sysSyncUserInfo);

    public ResponseEntity updateSysSyncUserInfo(SysSyncUserInfo sysSyncUserInfo);

    public ResponseEntity batchDeleteUpdateSysSyncUserInfo(List<SysSyncUserInfo> sysSyncUserInfoList);

    // --扩展
    public ResponseEntity jobsSysSyncUserInfo(JSONArray jsonArray);
}

