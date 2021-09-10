package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvComInf;

public interface ProInvComInfMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvComInf record);

    int insertSelective(ProInvComInf record);

    ProInvComInf selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvComInf record);

    int updateByPrimaryKey(ProInvComInf record);

    List<ProInvComInf> selectByProInvComInfList(ProInvComInf record);

    int batchDeleteUpdateProInvComInf(List<ProInvComInf> recordList, ProInvComInf record);

}

