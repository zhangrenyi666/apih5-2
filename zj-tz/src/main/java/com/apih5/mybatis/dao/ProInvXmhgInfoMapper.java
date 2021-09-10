package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvXmhgInfo;

public interface ProInvXmhgInfoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvXmhgInfo record);

    int insertSelective(ProInvXmhgInfo record);

    ProInvXmhgInfo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvXmhgInfo record);

    int updateByPrimaryKey(ProInvXmhgInfo record);

    List<ProInvXmhgInfo> selectByProInvXmhgInfoList(ProInvXmhgInfo record);

    int batchDeleteUpdateProInvXmhgInfo(List<ProInvXmhgInfo> recordList, ProInvXmhgInfo record);

}

