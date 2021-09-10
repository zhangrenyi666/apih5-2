package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxOaDeptMember;

public interface ZjXmCqjxOaDeptMemberMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxOaDeptMember record);

    int insertSelective(ZjXmCqjxOaDeptMember record);

    ZjXmCqjxOaDeptMember selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxOaDeptMember record);

    int updateByPrimaryKey(ZjXmCqjxOaDeptMember record);

    List<ZjXmCqjxOaDeptMember> selectByZjXmCqjxOaDeptMemberList(ZjXmCqjxOaDeptMember record);

    int batchDeleteUpdateZjXmCqjxOaDeptMember(List<ZjXmCqjxOaDeptMember> recordList, ZjXmCqjxOaDeptMember record);

}

