package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxCrCustomerInfoQa;

public interface ZxCrCustomerInfoQaMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrCustomerInfoQa record);

    int insertSelective(ZxCrCustomerInfoQa record);

    ZxCrCustomerInfoQa selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrCustomerInfoQa record);

    int updateByPrimaryKey(ZxCrCustomerInfoQa record);

    List<ZxCrCustomerInfoQa> selectByZxCrCustomerInfoQaList(ZxCrCustomerInfoQa record);

    int batchDeleteUpdateZxCrCustomerInfoQa(List<ZxCrCustomerInfoQa> recordList, ZxCrCustomerInfoQa record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
