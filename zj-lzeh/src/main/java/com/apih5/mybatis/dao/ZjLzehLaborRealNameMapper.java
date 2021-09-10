package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehLaborRealName;

public interface ZjLzehLaborRealNameMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehLaborRealName record);

    int insertSelective(ZjLzehLaborRealName record);

    ZjLzehLaborRealName selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehLaborRealName record);

    int updateByPrimaryKey(ZjLzehLaborRealName record);

    List<ZjLzehLaborRealName> selectByZjLzehLaborRealNameList(ZjLzehLaborRealName record);

    int batchDeleteUpdateZjLzehLaborRealName(List<ZjLzehLaborRealName> recordList, ZjLzehLaborRealName record);

}

