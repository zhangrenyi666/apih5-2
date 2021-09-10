package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;

public interface ZjXmCqjxDepartmentHeadDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxDepartmentHeadDetail record);

    int insertSelective(ZjXmCqjxDepartmentHeadDetail record);

    ZjXmCqjxDepartmentHeadDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxDepartmentHeadDetail record);

    int updateByPrimaryKey(ZjXmCqjxDepartmentHeadDetail record);

    List<ZjXmCqjxDepartmentHeadDetail> selectByZjXmCqjxDepartmentHeadDetailList(ZjXmCqjxDepartmentHeadDetail record);

    int batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(List<ZjXmCqjxDepartmentHeadDetail> recordList, ZjXmCqjxDepartmentHeadDetail record);

}

