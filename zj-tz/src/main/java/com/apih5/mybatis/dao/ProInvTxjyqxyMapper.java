package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvTxjyqxy;

public interface ProInvTxjyqxyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvTxjyqxy record);

    int insertSelective(ProInvTxjyqxy record);

    ProInvTxjyqxy selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvTxjyqxy record);

    int updateByPrimaryKey(ProInvTxjyqxy record);

    List<ProInvTxjyqxy> selectByProInvTxjyqxyList(ProInvTxjyqxy record);

    int batchDeleteUpdateProInvTxjyqxy(List<ProInvTxjyqxy> recordList, ProInvTxjyqxy record);

}

