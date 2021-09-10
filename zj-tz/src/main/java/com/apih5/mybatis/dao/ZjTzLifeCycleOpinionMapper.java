package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzLifeCycleOpinion;

public interface ZjTzLifeCycleOpinionMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzLifeCycleOpinion record);

    int insertSelective(ZjTzLifeCycleOpinion record);

    ZjTzLifeCycleOpinion selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzLifeCycleOpinion record);

    int updateByPrimaryKey(ZjTzLifeCycleOpinion record);

    List<ZjTzLifeCycleOpinion> selectByZjTzLifeCycleOpinionList(ZjTzLifeCycleOpinion record);

    int batchDeleteUpdateZjTzLifeCycleOpinion(List<ZjTzLifeCycleOpinion> recordList, ZjTzLifeCycleOpinion record);

}

