package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysProjectDeptConf;

public interface SysProjectDeptConfMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysProjectDeptConf record);

    int insertSelective(SysProjectDeptConf record);

    SysProjectDeptConf selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysProjectDeptConf record);

    int updateByPrimaryKey(SysProjectDeptConf record);

    List<SysProjectDeptConf> selectBySysProjectDeptConfList(SysProjectDeptConf record);

    int batchDeleteUpdateSysProjectDeptConf(List<SysProjectDeptConf> recordList, SysProjectDeptConf record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
