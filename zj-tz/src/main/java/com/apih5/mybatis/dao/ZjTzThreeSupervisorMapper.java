package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzThreeSupervisor;

public interface ZjTzThreeSupervisorMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzThreeSupervisor record);

    int insertSelective(ZjTzThreeSupervisor record);

    ZjTzThreeSupervisor selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzThreeSupervisor record);

    int updateByPrimaryKey(ZjTzThreeSupervisor record);

    List<ZjTzThreeSupervisor> selectByZjTzThreeSupervisorList(ZjTzThreeSupervisor record);

    int batchDeleteUpdateZjTzThreeSupervisor(List<ZjTzThreeSupervisor> recordList, ZjTzThreeSupervisor record);

	int updateZjTzThreeSupervisorProjectShortName(ZjTzThreeSupervisor supervisor);

}

