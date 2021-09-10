package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChange;

public interface ZjTzLifeCycleChangeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzLifeCycleChange record);

    int insertSelective(ZjTzLifeCycleChange record);

    ZjTzLifeCycleChange selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzLifeCycleChange record);

    int updateByPrimaryKey(ZjTzLifeCycleChange record);

    List<ZjTzLifeCycleChange> selectByZjTzLifeCycleChangeList(ZjTzLifeCycleChange record);

    int batchDeleteUpdateZjTzLifeCycleChange(List<ZjTzLifeCycleChange> recordList, ZjTzLifeCycleChange record);

	int selectLifeCycleChangeWorkListCount(ZjTzLifeCycleChange workId);

}

