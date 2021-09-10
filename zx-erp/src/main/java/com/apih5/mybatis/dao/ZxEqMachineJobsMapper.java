package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqMachineJobs;

public interface ZxEqMachineJobsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqMachineJobs record);

    int insertSelective(ZxEqMachineJobs record);

    ZxEqMachineJobs selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqMachineJobs record);

    int updateByPrimaryKey(ZxEqMachineJobs record);

    List<ZxEqMachineJobs> selectByZxEqMachineJobsList(ZxEqMachineJobs record);

    int batchDeleteUpdateZxEqMachineJobs(List<ZxEqMachineJobs> recordList, ZxEqMachineJobs record);

}

