package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxOaLeader;

public interface ZjXmCqjxOaLeaderMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxOaLeader record);

    int insertSelective(ZjXmCqjxOaLeader record);

    ZjXmCqjxOaLeader selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxOaLeader record);

    int updateByPrimaryKey(ZjXmCqjxOaLeader record);

    List<ZjXmCqjxOaLeader> selectByZjXmCqjxOaLeaderList(ZjXmCqjxOaLeader record);

    int batchDeleteUpdateZjXmCqjxOaLeader(List<ZjXmCqjxOaLeader> recordList, ZjXmCqjxOaLeader record);

}

