package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqIecrCustomerNew;

public interface ZxEqIecrCustomerNewMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqIecrCustomerNew record);

    int insertSelective(ZxEqIecrCustomerNew record);

    ZxEqIecrCustomerNew selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqIecrCustomerNew record);

    int updateByPrimaryKey(ZxEqIecrCustomerNew record);

    List<ZxEqIecrCustomerNew> selectByZxEqIecrCustomerNewList(ZxEqIecrCustomerNew record);

    int batchDeleteUpdateZxEqIecrCustomerNew(List<ZxEqIecrCustomerNew> recordList, ZxEqIecrCustomerNew record);

}

