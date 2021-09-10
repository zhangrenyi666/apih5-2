package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzLifeCycle;

public interface ZjTzLifeCycleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzLifeCycle record);

    int insertSelective(ZjTzLifeCycle record);

    ZjTzLifeCycle selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzLifeCycle record);

    int updateByPrimaryKey(ZjTzLifeCycle record);

    List<ZjTzLifeCycle> selectByZjTzLifeCycleList(ZjTzLifeCycle record);

    int batchDeleteUpdateZjTzLifeCycle(List<ZjTzLifeCycle> recordList, ZjTzLifeCycle record);

	int selectLifeCycleWorkListCount(ZjTzLifeCycle dbzjTzLifeCycle);

	List<ZjTzLifeCycle> selectLifeCycleWorkList(ZjTzLifeCycle dbzjTzLifeCycle);
}

