package com.apih5.mybatis.dao;

import com.apih5.mybatis.pojo.ZxSysProject;

public interface ZxSysProjectMapper {
    String getCompanyIdByProjectId(String projectId);

    ZxSysProject getCompanyInfoByProjectId(String projectId);
}
