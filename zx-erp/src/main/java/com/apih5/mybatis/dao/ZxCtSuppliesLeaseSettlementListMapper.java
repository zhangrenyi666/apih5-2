package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseCampChangeIncrease;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;

public interface ZxCtSuppliesLeaseSettlementListMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesLeaseSettlementList record);

    int insertSelective(ZxCtSuppliesLeaseSettlementList record);

    ZxCtSuppliesLeaseSettlementList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesLeaseSettlementList record);

    int updateByPrimaryKey(ZxCtSuppliesLeaseSettlementList record);

    List<ZxCtSuppliesLeaseSettlementList> selectByZxCtSuppliesLeaseSettlementListList(ZxCtSuppliesLeaseSettlementList record);
    
    int batchDeleteUpdateZxCtSuppliesLeaseSettlementList(List<ZxCtSuppliesLeaseSettlementList> recordList, ZxCtSuppliesLeaseSettlementList record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    ZxCtSuppliesLeaseSettlementList getDetailByWorkId(String key);
    
    List<ZxCtSuppliesLeaseCampChangeIncrease> selectZxCtSuppliesLeaseCampChangeIncreaseList(ZxCtSuppliesLeaseSettlementList record);
}
