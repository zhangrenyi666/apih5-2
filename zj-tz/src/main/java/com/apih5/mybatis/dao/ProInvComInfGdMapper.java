package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvComInfGd;

public interface ProInvComInfGdMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvComInfGd record);

    int insertSelective(ProInvComInfGd record);

    ProInvComInfGd selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvComInfGd record);

    int updateByPrimaryKey(ProInvComInfGd record);

    List<ProInvComInfGd> selectByProInvComInfGdList(ProInvComInfGd record);

    int batchDeleteUpdateProInvComInfGd(List<ProInvComInfGd> recordList, ProInvComInfGd record);

}

