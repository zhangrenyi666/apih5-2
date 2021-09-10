package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.BaseFlowPerson;

public interface BaseFlowPersonMapper {
    int deleteByPrimaryKey(String key);

    int insert(BaseFlowPerson record);

    int insertSelective(BaseFlowPerson record);

    BaseFlowPerson selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(BaseFlowPerson record);

    int updateByPrimaryKey(BaseFlowPerson record);

    List<BaseFlowPerson> selectByBaseFlowPersonList(BaseFlowPerson record);

    int batchDeleteUpdateBaseFlowPerson(List<BaseFlowPerson> recordList);

}

