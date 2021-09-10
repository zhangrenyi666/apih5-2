package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssistantLeaderApproval;

public interface ZjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectDisciplineAssistantLeaderApproval record);

    int insertSelective(ZjXmCqjxProjectDisciplineAssistantLeaderApproval record);

    ZjXmCqjxProjectDisciplineAssistantLeaderApproval selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectDisciplineAssistantLeaderApproval record);

    int updateByPrimaryKey(ZjXmCqjxProjectDisciplineAssistantLeaderApproval record);

    List<ZjXmCqjxProjectDisciplineAssistantLeaderApproval> selectByZjXmCqjxProjectDisciplineAssistantLeaderApprovalList(ZjXmCqjxProjectDisciplineAssistantLeaderApproval record);

    int batchDeleteUpdateZjXmCqjxProjectDisciplineAssistantLeaderApproval(List<ZjXmCqjxProjectDisciplineAssistantLeaderApproval> recordList, ZjXmCqjxProjectDisciplineAssistantLeaderApproval record);

}

