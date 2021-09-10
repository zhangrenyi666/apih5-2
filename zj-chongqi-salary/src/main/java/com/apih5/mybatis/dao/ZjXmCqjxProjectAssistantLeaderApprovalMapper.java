package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssistantLeaderApproval;

public interface ZjXmCqjxProjectAssistantLeaderApprovalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectAssistantLeaderApproval record);

    int insertSelective(ZjXmCqjxProjectAssistantLeaderApproval record);

    ZjXmCqjxProjectAssistantLeaderApproval selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectAssistantLeaderApproval record);

    int updateByPrimaryKey(ZjXmCqjxProjectAssistantLeaderApproval record);

    List<ZjXmCqjxProjectAssistantLeaderApproval> selectByZjXmCqjxProjectAssistantLeaderApprovalList(ZjXmCqjxProjectAssistantLeaderApproval record);

    int batchDeleteUpdateZjXmCqjxProjectAssistantLeaderApproval(List<ZjXmCqjxProjectAssistantLeaderApproval> recordList, ZjXmCqjxProjectAssistantLeaderApproval record);

}

