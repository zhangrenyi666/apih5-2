package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysWoaFlowSelect;

public interface SysWoaFlowSelectMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysWoaFlowSelect record);

    int insertSelective(SysWoaFlowSelect record);

    SysWoaFlowSelect selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysWoaFlowSelect record);

    int updateByPrimaryKey(SysWoaFlowSelect record);

    List<SysWoaFlowSelect> selectBySysWoaFlowSelectList(SysWoaFlowSelect record);

    int batchDeleteUpdateSysWoaFlowSelect(List<SysWoaFlowSelect> recordList);

}

