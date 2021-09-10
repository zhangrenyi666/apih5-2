package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.framework.api.sysdb.entity.SysFiles;

public interface SysFilesMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysFiles record);

    int insertSelective(SysFiles record);

    SysFiles selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysFiles record);

    int updateByPrimaryKey(SysFiles record);

    List<SysFiles> selectBySysFilesList(SysFiles record);

    int batchDeleteUpdateSysFiles(List<SysFiles> recordList);
    
    int batchInsertSysFiles(List<SysFiles> recordList);
    
    int deleteSysFilesByOtherId(String key);

}

