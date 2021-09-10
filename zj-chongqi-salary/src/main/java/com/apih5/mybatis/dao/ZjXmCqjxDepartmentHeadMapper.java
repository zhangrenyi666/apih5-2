package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;

public interface ZjXmCqjxDepartmentHeadMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxDepartmentHead record);

    int insertSelective(ZjXmCqjxDepartmentHead record);

    ZjXmCqjxDepartmentHead selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxDepartmentHead record);

    int updateByPrimaryKey(ZjXmCqjxDepartmentHead record);

    List<ZjXmCqjxDepartmentHead> selectByZjXmCqjxDepartmentHeadList(ZjXmCqjxDepartmentHead record);

    int batchDeleteUpdateZjXmCqjxDepartmentHead(List<ZjXmCqjxDepartmentHead> recordList, ZjXmCqjxDepartmentHead record);

}

