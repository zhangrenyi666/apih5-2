package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxYearScoreApproval;

public interface ZjXmCqjxYearScoreApprovalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxYearScoreApproval record);

    int insertSelective(ZjXmCqjxYearScoreApproval record);

    ZjXmCqjxYearScoreApproval selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxYearScoreApproval record);

    int updateByPrimaryKey(ZjXmCqjxYearScoreApproval record);

    List<ZjXmCqjxYearScoreApproval> selectByZjXmCqjxYearScoreApprovalList(ZjXmCqjxYearScoreApproval record);

    int batchDeleteUpdateZjXmCqjxYearScoreApproval(List<ZjXmCqjxYearScoreApproval> recordList, ZjXmCqjxYearScoreApproval record);

}

