package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehTeam;

public interface ZjLzehTeamMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehTeam record);

    int insertSelective(ZjLzehTeam record);

    ZjLzehTeam selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehTeam record);

    int updateByPrimaryKey(ZjLzehTeam record);

    List<ZjLzehTeam> selectByZjLzehTeamList(ZjLzehTeam record);

    int batchDeleteUpdateZjLzehTeam(List<ZjLzehTeam> recordList, ZjLzehTeam record);

}

