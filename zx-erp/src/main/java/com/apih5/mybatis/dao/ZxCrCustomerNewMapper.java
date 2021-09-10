package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrCustomerNew;

public interface ZxCrCustomerNewMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrCustomerNew record);

    int insertSelective(ZxCrCustomerNew record);

    ZxCrCustomerNew selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrCustomerNew record);

    int updateByPrimaryKey(ZxCrCustomerNew record);

    List<ZxCrCustomerNew> selectByZxCrCustomerNewList(ZxCrCustomerNew record);

    int batchDeleteUpdateZxCrCustomerNew(List<ZxCrCustomerNew> recordList, ZxCrCustomerNew record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
