package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.framework.api.sysdb.entity.SysCompany;

public interface SysCompanyMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysCompany record);

    int insertSelective(SysCompany record);

    SysCompany selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysCompany record);

    int updateByPrimaryKey(SysCompany record);

    List<SysCompany> selectBySysCompanyList(SysCompany record);
    
    List getSysCompanySelect(SysCompany record);

    int batchDeleteUpdateSysCompany(List<SysCompany> recordList);
    
    SysCompany getSysCompanyByUserKey(SysCompany record);

}

