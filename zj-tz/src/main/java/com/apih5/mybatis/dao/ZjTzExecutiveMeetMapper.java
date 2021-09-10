package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzExecutiveMeet;

public interface ZjTzExecutiveMeetMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzExecutiveMeet record);

    int insertSelective(ZjTzExecutiveMeet record);

    ZjTzExecutiveMeet selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzExecutiveMeet record);

    int updateByPrimaryKey(ZjTzExecutiveMeet record);

    List<ZjTzExecutiveMeet> selectByZjTzExecutiveMeetList(ZjTzExecutiveMeet record);

    int batchDeleteUpdateZjTzExecutiveMeet(List<ZjTzExecutiveMeet> recordList, ZjTzExecutiveMeet record);

}

