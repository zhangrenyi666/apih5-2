package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysWoaAddFlow;

public interface SysWoaAddFlowMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysWoaAddFlow record);

    int insertSelective(SysWoaAddFlow record);

    SysWoaAddFlow selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysWoaAddFlow record);

    int updateByPrimaryKey(SysWoaAddFlow record);

    List<SysWoaAddFlow> selectBySysWoaAddFlowList(SysWoaAddFlow record);

    int batchDeleteUpdateSysWoaAddFlow(List<SysWoaAddFlow> recordList);

    List<SysWoaAddFlow> selectBySysWoaAddFlowListByRole(SysWoaAddFlow record);
}

