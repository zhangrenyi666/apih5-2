package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHeadDetail;

public interface ZjXmCqjxProjectDepartmentHeadDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectDepartmentHeadDetail record);

    int insertSelective(ZjXmCqjxProjectDepartmentHeadDetail record);

    ZjXmCqjxProjectDepartmentHeadDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectDepartmentHeadDetail record);

    int updateByPrimaryKey(ZjXmCqjxProjectDepartmentHeadDetail record);

    List<ZjXmCqjxProjectDepartmentHeadDetail> selectByZjXmCqjxProjectDepartmentHeadDetailList(ZjXmCqjxProjectDepartmentHeadDetail record);

    int batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(List<ZjXmCqjxProjectDepartmentHeadDetail> recordList, ZjXmCqjxProjectDepartmentHeadDetail record);

}

