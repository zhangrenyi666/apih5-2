package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChangeOpinion;

public interface ZjTzLifeCycleChangeOpinionMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzLifeCycleChangeOpinion record);

    int insertSelective(ZjTzLifeCycleChangeOpinion record);

    ZjTzLifeCycleChangeOpinion selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzLifeCycleChangeOpinion record);

    int updateByPrimaryKey(ZjTzLifeCycleChangeOpinion record);

    List<ZjTzLifeCycleChangeOpinion> selectByZjTzLifeCycleChangeOpinionList(ZjTzLifeCycleChangeOpinion record);

    int batchDeleteUpdateZjTzLifeCycleChangeOpinion(List<ZjTzLifeCycleChangeOpinion> recordList, ZjTzLifeCycleChangeOpinion record);

}

