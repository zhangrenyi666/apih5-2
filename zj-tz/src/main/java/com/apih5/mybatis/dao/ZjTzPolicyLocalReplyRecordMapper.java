package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPolicyLocalReplyRecord;

public interface ZjTzPolicyLocalReplyRecordMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPolicyLocalReplyRecord record);

    int insertSelective(ZjTzPolicyLocalReplyRecord record);

    ZjTzPolicyLocalReplyRecord selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPolicyLocalReplyRecord record);

    int updateByPrimaryKey(ZjTzPolicyLocalReplyRecord record);

    List<ZjTzPolicyLocalReplyRecord> selectByZjTzPolicyLocalReplyRecordList(ZjTzPolicyLocalReplyRecord record);

    int batchDeleteUpdateZjTzPolicyLocalReplyRecord(List<ZjTzPolicyLocalReplyRecord> recordList, ZjTzPolicyLocalReplyRecord record);

	int updateZjTzPolicyLocalReplyRecordProjectShortName(ZjTzPolicyLocalReplyRecord localReplyRecord);

}

