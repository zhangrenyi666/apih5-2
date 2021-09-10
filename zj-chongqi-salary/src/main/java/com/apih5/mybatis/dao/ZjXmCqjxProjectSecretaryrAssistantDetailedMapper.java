package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxAssistantReportBean;
import com.apih5.mybatis.pojo.ZjXmCqjxDeptLeaderAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSecretaryrAssistantDetailed;

public interface ZjXmCqjxProjectSecretaryrAssistantDetailedMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectSecretaryrAssistantDetailed record);

    int insertSelective(ZjXmCqjxProjectSecretaryrAssistantDetailed record);

    ZjXmCqjxProjectSecretaryrAssistantDetailed selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectSecretaryrAssistantDetailed record);

    int updateByPrimaryKey(ZjXmCqjxProjectSecretaryrAssistantDetailed record);

    List<ZjXmCqjxProjectSecretaryrAssistantDetailed> selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(ZjXmCqjxProjectSecretaryrAssistantDetailed record);

    int batchDeleteUpdateZjXmCqjxProjectSecretaryrAssistantDetailed(List<ZjXmCqjxProjectSecretaryrAssistantDetailed> recordList, ZjXmCqjxProjectSecretaryrAssistantDetailed record);
    
    List<ZjXmCqjxAssistantReportBean> selectZjXmCqjxDeptLeaderDetailedRportListByQuarter(ZjXmCqjxDeptLeaderAssistantDetailed record);

}

