package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPolicyCountryReplyRecord;

public interface ZjTzPolicyCountryReplyRecordMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPolicyCountryReplyRecord record);

    int insertSelective(ZjTzPolicyCountryReplyRecord record);

    ZjTzPolicyCountryReplyRecord selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPolicyCountryReplyRecord record);

    int updateByPrimaryKey(ZjTzPolicyCountryReplyRecord record);

    List<ZjTzPolicyCountryReplyRecord> selectByZjTzPolicyCountryReplyRecordList(ZjTzPolicyCountryReplyRecord record);

    int batchDeleteUpdateZjTzPolicyCountryReplyRecord(List<ZjTzPolicyCountryReplyRecord> recordList, ZjTzPolicyCountryReplyRecord record);

}

