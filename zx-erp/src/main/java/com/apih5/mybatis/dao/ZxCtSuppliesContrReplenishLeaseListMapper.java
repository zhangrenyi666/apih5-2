package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishLeaseList;

public interface ZxCtSuppliesContrReplenishLeaseListMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContrReplenishLeaseList record);

    int insertSelective(ZxCtSuppliesContrReplenishLeaseList record);

    ZxCtSuppliesContrReplenishLeaseList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContrReplenishLeaseList record);

    int updateByPrimaryKey(ZxCtSuppliesContrReplenishLeaseList record);

    List<ZxCtSuppliesContrReplenishLeaseList> selectByZxCtSuppliesContrReplenishLeaseListList(ZxCtSuppliesContrReplenishLeaseList record);

    int batchDeleteUpdateZxCtSuppliesContrReplenishLeaseList(List<ZxCtSuppliesContrReplenishLeaseList> recordList, ZxCtSuppliesContrReplenishLeaseList record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
