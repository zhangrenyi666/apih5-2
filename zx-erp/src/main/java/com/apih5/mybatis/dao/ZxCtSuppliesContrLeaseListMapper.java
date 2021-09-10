package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrLeaseList;

public interface ZxCtSuppliesContrLeaseListMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContrLeaseList record);

    int insertSelective(ZxCtSuppliesContrLeaseList record);

    ZxCtSuppliesContrLeaseList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContrLeaseList record);

    int updateByPrimaryKey(ZxCtSuppliesContrLeaseList record);

    List<ZxCtSuppliesContrLeaseList> selectByZxCtSuppliesContrLeaseListList(ZxCtSuppliesContrLeaseList record);

    int batchDeleteUpdateZxCtSuppliesContrLeaseList(List<ZxCtSuppliesContrLeaseList> recordList, ZxCtSuppliesContrLeaseList record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
