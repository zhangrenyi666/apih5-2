package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrCustomerHonor;

public interface ZxCrCustomerHonorMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrCustomerHonor record);

    int insertSelective(ZxCrCustomerHonor record);

    ZxCrCustomerHonor selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrCustomerHonor record);

    int updateByPrimaryKey(ZxCrCustomerHonor record);

    List<ZxCrCustomerHonor> selectByZxCrCustomerHonorList(ZxCrCustomerHonor record);

    int batchDeleteUpdateZxCrCustomerHonor(List<ZxCrCustomerHonor> recordList, ZxCrCustomerHonor record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
