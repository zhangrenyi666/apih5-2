package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseCampChangeIncrease;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;

public interface ZxCtSuppliesShopSettlementListMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesShopSettlementList record);

    int insertSelective(ZxCtSuppliesShopSettlementList record);

    ZxCtSuppliesShopSettlementList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesShopSettlementList record);

    int updateByPrimaryKey(ZxCtSuppliesShopSettlementList record);

    List<ZxCtSuppliesShopSettlementList> selectByZxCtSuppliesShopSettlementListList(ZxCtSuppliesShopSettlementList record);
    
    List<ZxCtSuppliesLeaseCampChangeIncrease> selectZxCtSuppliesShopCampChangeIncreaseList(ZxCtSuppliesShopSettlementList record);

    int batchDeleteUpdateZxCtSuppliesShopSettlementList(List<ZxCtSuppliesShopSettlementList> recordList, ZxCtSuppliesShopSettlementList record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    ZxCtSuppliesShopSettlementList getDetailByWorkId(String key);
}
