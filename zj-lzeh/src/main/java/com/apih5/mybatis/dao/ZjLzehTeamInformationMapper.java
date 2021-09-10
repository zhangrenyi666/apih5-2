package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehTeamInformation;

public interface ZjLzehTeamInformationMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehTeamInformation record);

    int insertSelective(ZjLzehTeamInformation record);

    ZjLzehTeamInformation selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehTeamInformation record);

    int updateByPrimaryKey(ZjLzehTeamInformation record);

    List<ZjLzehTeamInformation> selectByZjLzehTeamInformationList(ZjLzehTeamInformation record);

    int batchDeleteUpdateZjLzehTeamInformation(List<ZjLzehTeamInformation> recordList, ZjLzehTeamInformation record);

    void updateZjLzehTeamInformationNumber(ZjLzehTeamInformation zjLzehTeamInformation);

}

