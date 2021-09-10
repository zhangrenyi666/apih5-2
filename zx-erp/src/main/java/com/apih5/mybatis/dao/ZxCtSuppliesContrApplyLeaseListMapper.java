package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyLeaseList;

public interface ZxCtSuppliesContrApplyLeaseListMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContrApplyLeaseList record);

    int insertSelective(ZxCtSuppliesContrApplyLeaseList record);

    ZxCtSuppliesContrApplyLeaseList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContrApplyLeaseList record);

    int updateByPrimaryKey(ZxCtSuppliesContrApplyLeaseList record);

    List<ZxCtSuppliesContrApplyLeaseList> selectByZxCtSuppliesContrApplyLeaseListList(ZxCtSuppliesContrApplyLeaseList record);

    int batchDeleteUpdateZxCtSuppliesContrApplyLeaseList(List<ZxCtSuppliesContrApplyLeaseList> recordList, ZxCtSuppliesContrApplyLeaseList record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxCtSuppliesContrApplyLeaseList> selectSuppliesContrApplyLeaseListByCondition(ZxCtSuppliesContrApplyLeaseList record);
}
