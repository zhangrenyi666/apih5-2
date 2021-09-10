package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPppTermReply;

public interface ZjTzPppTermReplyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPppTermReply record);

    int insertSelective(ZjTzPppTermReply record);

    ZjTzPppTermReply selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPppTermReply record);

    int updateByPrimaryKey(ZjTzPppTermReply record);

    List<ZjTzPppTermReply> selectByZjTzPppTermReplyList(ZjTzPppTermReply record);

    int batchDeleteUpdateZjTzPppTermReply(List<ZjTzPppTermReply> recordList, ZjTzPppTermReply record);

    List<ZjTzPppTermReply> selectByZjTzPppTermReplyListDelFlag(ZjTzPppTermReply record);
}

