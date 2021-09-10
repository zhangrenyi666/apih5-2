package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPolicyLocalReply;

public interface ZjTzPolicyLocalReplyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPolicyLocalReply record);

    int insertSelective(ZjTzPolicyLocalReply record);

    ZjTzPolicyLocalReply selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPolicyLocalReply record);

    int updateByPrimaryKey(ZjTzPolicyLocalReply record);

    List<ZjTzPolicyLocalReply> selectByZjTzPolicyLocalReplyList(ZjTzPolicyLocalReply record);

    int batchDeleteUpdateZjTzPolicyLocalReply(List<ZjTzPolicyLocalReply> recordList, ZjTzPolicyLocalReply record);

	int updateZjTzPolicyLocalReplyProjectShortName(ZjTzPolicyLocalReply localReply);

}

