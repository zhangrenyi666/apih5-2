package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.framework.entity.SysSequence;

public interface SysSequenceMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysSequence record);

    int insertSelective(SysSequence record);

    SysSequence selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysSequence record);

    int updateByPrimaryKey(SysSequence record);

    List<SysSequence> selectBySysSequenceList(SysSequence record);

    int batchDeleteUpdateSysSequence(List<SysSequence> recordList);
 
//    List selectBySysSequenceList(SysSequence record);

    int selectBySysSequenceTotalNum(SysSequence record);

    int batchDeleteSysSequence(List<SysSequence> recordList);
    
    int selectCurrval(SysSequence record);

    int selectNextval(SysSequence record);

}

