package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjLzehTeam;
import com.apih5.mybatis.pojo.ZjLzehTeamInformationTeam;

public interface ZjLzehTeamInformationTeamMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehTeamInformationTeam record);

    int insertSelective(ZjLzehTeamInformationTeam record);

    ZjLzehTeamInformationTeam selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehTeamInformationTeam record);

    int updateByPrimaryKey(ZjLzehTeamInformationTeam record);

    List<ZjLzehTeamInformationTeam> selectByZjLzehTeamInformationTeamList(ZjLzehTeamInformationTeam record);

    int batchDeleteUpdateZjLzehTeamInformationTeam(List<ZjLzehTeamInformationTeam> recordList, ZjLzehTeamInformationTeam record);

    int batchDeleteUpdateZjLzehTeamInformationAndTeam(ZjLzehTeamInformationTeam record, ZjLzehTeamInformationTeam record2);

}

