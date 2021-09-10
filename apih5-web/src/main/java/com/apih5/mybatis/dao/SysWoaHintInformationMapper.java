package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysWoaHintInformation;

public interface SysWoaHintInformationMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysWoaHintInformation record);

    int insertSelective(SysWoaHintInformation record);

    SysWoaHintInformation selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysWoaHintInformation record);

    int updateByPrimaryKey(SysWoaHintInformation record);

    List<SysWoaHintInformation> selectBySysWoaHintInformationList(SysWoaHintInformation record);

    int batchDeleteUpdateSysWoaHintInformation(List<SysWoaHintInformation> recordList);

    List<SysWoaHintInformation> selectBySysWoaHintInformationListByTime(SysWoaHintInformation record);
}

