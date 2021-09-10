package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;

public interface ZjXmCqjxAssistantLeaderApprovalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxAssistantLeaderApproval record);

    int insertSelective(ZjXmCqjxAssistantLeaderApproval record);

    ZjXmCqjxAssistantLeaderApproval selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxAssistantLeaderApproval record);

    int updateByPrimaryKey(ZjXmCqjxAssistantLeaderApproval record);

    List<ZjXmCqjxAssistantLeaderApproval> selectByZjXmCqjxAssistantLeaderApprovalList(ZjXmCqjxAssistantLeaderApproval record);

    int batchDeleteUpdateZjXmCqjxAssistantLeaderApproval(List<ZjXmCqjxAssistantLeaderApproval> recordList, ZjXmCqjxAssistantLeaderApproval record);

}

