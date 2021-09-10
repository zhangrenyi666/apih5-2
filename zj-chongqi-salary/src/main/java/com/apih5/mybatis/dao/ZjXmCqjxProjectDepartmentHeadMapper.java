package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHead;

public interface ZjXmCqjxProjectDepartmentHeadMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectDepartmentHead record);

    int insertSelective(ZjXmCqjxProjectDepartmentHead record);

    ZjXmCqjxProjectDepartmentHead selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectDepartmentHead record);

    int updateByPrimaryKey(ZjXmCqjxProjectDepartmentHead record);

    List<ZjXmCqjxProjectDepartmentHead> selectByZjXmCqjxProjectDepartmentHeadList(ZjXmCqjxProjectDepartmentHead record);

    int batchDeleteUpdateZjXmCqjxProjectDepartmentHead(List<ZjXmCqjxProjectDepartmentHead> recordList, ZjXmCqjxProjectDepartmentHead record);

}

