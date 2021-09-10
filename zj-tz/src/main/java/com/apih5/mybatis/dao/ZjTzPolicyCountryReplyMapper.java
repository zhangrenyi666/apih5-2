package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPolicyCountryReply;

public interface ZjTzPolicyCountryReplyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPolicyCountryReply record);

    int insertSelective(ZjTzPolicyCountryReply record);

    ZjTzPolicyCountryReply selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPolicyCountryReply record);

    int updateByPrimaryKey(ZjTzPolicyCountryReply record);

    List<ZjTzPolicyCountryReply> selectByZjTzPolicyCountryReplyList(ZjTzPolicyCountryReply record);

    int batchDeleteUpdateZjTzPolicyCountryReply(List<ZjTzPolicyCountryReply> recordList, ZjTzPolicyCountryReply record);

}

