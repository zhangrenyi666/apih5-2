package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrCustomerInfo;

public interface ZxCrCustomerInfoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrCustomerInfo record);

    int insertSelective(ZxCrCustomerInfo record);

    ZxCrCustomerInfo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrCustomerInfo record);

    int updateByPrimaryKey(ZxCrCustomerInfo record);

    List<ZxCrCustomerInfo> selectByZxCrCustomerInfoList(ZxCrCustomerInfo record);

    int batchDeleteUpdateZxCrCustomerInfo(List<ZxCrCustomerInfo> recordList, ZxCrCustomerInfo record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxCrCustomerInfo> selectAll();
    
    List<ZxCrCustomerInfo> selectByZxCrCustomerInfoListOne(ZxCrCustomerInfo record);
    
}
