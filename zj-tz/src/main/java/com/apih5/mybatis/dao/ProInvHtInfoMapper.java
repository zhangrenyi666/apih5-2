package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvHtInfo;

public interface ProInvHtInfoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvHtInfo record);

    int insertSelective(ProInvHtInfo record);

    ProInvHtInfo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvHtInfo record);

    int updateByPrimaryKey(ProInvHtInfo record);

    List<ProInvHtInfo> selectByProInvHtInfoList(ProInvHtInfo record);

    int batchDeleteUpdateProInvHtInfo(List<ProInvHtInfo> recordList, ProInvHtInfo record);

}

