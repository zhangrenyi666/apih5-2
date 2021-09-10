package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqMoveSupervisor;

public interface ZxEqMoveSupervisorMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqMoveSupervisor record);

    int insertSelective(ZxEqMoveSupervisor record);

    ZxEqMoveSupervisor selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqMoveSupervisor record);

    int updateByPrimaryKey(ZxEqMoveSupervisor record);

    List<ZxEqMoveSupervisor> selectByZxEqMoveSupervisorList(ZxEqMoveSupervisor record);

    int batchDeleteUpdateZxEqMoveSupervisor(List<ZxEqMoveSupervisor> recordList, ZxEqMoveSupervisor record);

	int batchRequestZxEqMoveSupervisorNum(List<ZxEqMoveSupervisor> zxEqMoveSupervisorList,ZxEqMoveSupervisor zxEqMoveSupervisor);

}

