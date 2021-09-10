package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtBalance;

public interface ZxCtBalanceMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtBalance record);

    int insertSelective(ZxCtBalance record);

    ZxCtBalance selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtBalance record);

    int updateByPrimaryKey(ZxCtBalance record);

    List<ZxCtBalance> selectByZxCtBalanceList(ZxCtBalance record);

    int batchDeleteUpdateZxCtBalance(List<ZxCtBalance> recordList, ZxCtBalance record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int updatebalAmtByPrimaryKey(String balID);
}
