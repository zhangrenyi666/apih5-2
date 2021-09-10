package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOaDeptMember;

public interface ZjXmCqjxProjectOaDeptMemberMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectOaDeptMember record);

    int insertSelective(ZjXmCqjxProjectOaDeptMember record);

    ZjXmCqjxProjectOaDeptMember selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectOaDeptMember record);

    int updateByPrimaryKey(ZjXmCqjxProjectOaDeptMember record);

    List<ZjXmCqjxProjectOaDeptMember> selectByZjXmCqjxProjectOaDeptMemberList(ZjXmCqjxProjectOaDeptMember record);

    int batchDeleteUpdateZjXmCqjxProjectOaDeptMember(List<ZjXmCqjxProjectOaDeptMember> recordList, ZjXmCqjxProjectOaDeptMember record);

}

