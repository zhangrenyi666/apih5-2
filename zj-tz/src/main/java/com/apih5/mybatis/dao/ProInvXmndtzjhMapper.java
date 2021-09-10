package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvXmndtzjh;

public interface ProInvXmndtzjhMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvXmndtzjh record);

    int insertSelective(ProInvXmndtzjh record);

    ProInvXmndtzjh selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvXmndtzjh record);

    int updateByPrimaryKey(ProInvXmndtzjh record);

    List<ProInvXmndtzjh> selectByProInvXmndtzjhList(ProInvXmndtzjh record);

    int batchDeleteUpdateProInvXmndtzjh(List<ProInvXmndtzjh> recordList, ProInvXmndtzjh record);

}

