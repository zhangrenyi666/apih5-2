package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxProjectSuperviseApproval;

public interface ZjXmCqjxProjectSuperviseApprovalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectSuperviseApproval record);

    int insertSelective(ZjXmCqjxProjectSuperviseApproval record);

    ZjXmCqjxProjectSuperviseApproval selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectSuperviseApproval record);

    int updateByPrimaryKey(ZjXmCqjxProjectSuperviseApproval record);

    List<ZjXmCqjxProjectSuperviseApproval> selectByZjXmCqjxProjectSuperviseApprovalList(ZjXmCqjxProjectSuperviseApproval record);

    int batchDeleteUpdateZjXmCqjxProjectSuperviseApproval(List<ZjXmCqjxProjectSuperviseApproval> recordList, ZjXmCqjxProjectSuperviseApproval record);
    
    int selectProjectSuperviseLeaderTodoCount(ZjXmCqjxProjectSuperviseApproval record);    

}

