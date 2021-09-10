package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvLrsxqk;

public interface ProInvLrsxqkMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvLrsxqk record);

    int insertSelective(ProInvLrsxqk record);

    ProInvLrsxqk selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvLrsxqk record);

    int updateByPrimaryKey(ProInvLrsxqk record);

    List<ProInvLrsxqk> selectByProInvLrsxqkList(ProInvLrsxqk record);

    int batchDeleteUpdateProInvLrsxqk(List<ProInvLrsxqk> recordList, ProInvLrsxqk record);

}

