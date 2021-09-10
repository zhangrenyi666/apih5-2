package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssistantLeaderApproval;

public interface ZjXmCqjxDisciplineAssistantLeaderApprovalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxDisciplineAssistantLeaderApproval record);

    int insertSelective(ZjXmCqjxDisciplineAssistantLeaderApproval record);

    ZjXmCqjxDisciplineAssistantLeaderApproval selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxDisciplineAssistantLeaderApproval record);

    int updateByPrimaryKey(ZjXmCqjxDisciplineAssistantLeaderApproval record);

    List<ZjXmCqjxDisciplineAssistantLeaderApproval> selectByZjXmCqjxDisciplineAssistantLeaderApprovalList(ZjXmCqjxDisciplineAssistantLeaderApproval record);

    int batchDeleteUpdateZjXmCqjxDisciplineAssistantLeaderApproval(List<ZjXmCqjxDisciplineAssistantLeaderApproval> recordList, ZjXmCqjxDisciplineAssistantLeaderApproval record);

}

