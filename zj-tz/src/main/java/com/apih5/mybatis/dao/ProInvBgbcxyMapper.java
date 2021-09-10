package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvBgbcxy;

public interface ProInvBgbcxyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvBgbcxy record);

    int insertSelective(ProInvBgbcxy record);

    ProInvBgbcxy selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvBgbcxy record);

    int updateByPrimaryKey(ProInvBgbcxy record);

    List<ProInvBgbcxy> selectByProInvBgbcxyList(ProInvBgbcxy record);

    int batchDeleteUpdateProInvBgbcxy(List<ProInvBgbcxy> recordList, ProInvBgbcxy record);

}

