package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjTzEmployeeEducation;
import com.apih5.mybatis.pojo.ZjTzEmployeeInfo;
import org.apache.ibatis.annotations.Param;

public interface ZjTzEmployeeInfoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzEmployeeInfo record);

    int insertSelective(ZjTzEmployeeInfo record);

    ZjTzEmployeeInfo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzEmployeeInfo record);

    int updateByPrimaryKey(ZjTzEmployeeInfo record);

    List<ZjTzEmployeeInfo> selectByZjTzEmployeeInfoList(ZjTzEmployeeInfo record);

    int batchDeleteUpdateZjTzEmployeeInfo(List<ZjTzEmployeeInfo> recordList, ZjTzEmployeeInfo record);

    List<ZjTzEmployeeInfo> selectByZjTzEmployeeInfoListAll(ZjTzEmployeeInfo record);

    ZjTzEmployeeInfo selectByIdCard(String idCard);

    List<ZjTzEmployeeInfo> selectZjTzEmployeeInfoListSelectPerson(ZjTzEmployeeInfo zjTzEmployeeInfo);

    ZjTzEmployeeInfo getZjTzEmployeeInfoByIdCard(ZjTzEmployeeInfo zjTzEmployeeInfo);

    List<ZjTzEmployeeInfo> isProject(@Param("zjTzEmployeeInfoList")List<ZjTzEmployeeInfo> zjTzEmployeeInfoList);

    int updateJoinTimeAndProject(ZjTzEmployeeInfo zjTzEmployeeInfo);




//    ZjTzEmployeeEducation getSubItems(ZjTzEmployeeEducation zjTzEmployeeEducation);

}

