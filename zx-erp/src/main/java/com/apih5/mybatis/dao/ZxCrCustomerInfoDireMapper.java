package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrCustomerInfoDire;

public interface ZxCrCustomerInfoDireMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrCustomerInfoDire record);

    int insertSelective(ZxCrCustomerInfoDire record);

    ZxCrCustomerInfoDire selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrCustomerInfoDire record);

    int updateByPrimaryKey(ZxCrCustomerInfoDire record);

    List<ZxCrCustomerInfoDire> selectByZxCrCustomerInfoDireList(ZxCrCustomerInfoDire record);

    int batchDeleteUpdateZxCrCustomerInfoDire(List<ZxCrCustomerInfoDire> recordList, ZxCrCustomerInfoDire record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
