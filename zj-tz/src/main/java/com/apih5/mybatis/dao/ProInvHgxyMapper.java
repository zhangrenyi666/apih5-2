package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvHgxy;

public interface ProInvHgxyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvHgxy record);

    int insertSelective(ProInvHgxy record);

    ProInvHgxy selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvHgxy record);

    int updateByPrimaryKey(ProInvHgxy record);

    List<ProInvHgxy> selectByProInvHgxyList(ProInvHgxy record);

    int batchDeleteUpdateProInvHgxy(List<ProInvHgxy> recordList, ProInvHgxy record);

}

