package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPppTermBase;

public interface ZjTzPppTermBaseMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPppTermBase record);

    int insertSelective(ZjTzPppTermBase record);

    ZjTzPppTermBase selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPppTermBase record);

    int updateByPrimaryKey(ZjTzPppTermBase record);

    List<ZjTzPppTermBase> selectByZjTzPppTermBaseList(ZjTzPppTermBase record);

    int batchDeleteUpdateZjTzPppTermBase(List<ZjTzPppTermBase> recordList, ZjTzPppTermBase record);

	List<ZjTzPppTermBase> getByZjTzPppTermBaseTermList(ZjTzPppTermBase termReply);

}

