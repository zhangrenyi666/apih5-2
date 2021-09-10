package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvBasic;

public interface ProInvBasicMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvBasic record);

    int insertSelective(ProInvBasic record);

    ProInvBasic selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvBasic record);

    int updateByPrimaryKey(ProInvBasic record);

    List<ProInvBasic> selectByProInvBasicList(ProInvBasic record);

    int batchDeleteUpdateProInvBasic(List<ProInvBasic> recordList, ProInvBasic record);

	ProInvBasic getProInvBasicByProjectId(String projectId);

}

