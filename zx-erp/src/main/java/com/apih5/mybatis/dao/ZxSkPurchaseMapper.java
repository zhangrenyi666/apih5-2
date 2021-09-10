package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkPurchase;

public interface ZxSkPurchaseMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkPurchase record);

    int insertSelective(ZxSkPurchase record);

    ZxSkPurchase selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkPurchase record);

    int updateByPrimaryKey(ZxSkPurchase record);

    List<ZxSkPurchase> selectByZxSkPurchaseList(ZxSkPurchase record);

    int batchDeleteUpdateZxSkPurchase(List<ZxSkPurchase> recordList, ZxSkPurchase record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int getZxSkPurchaseCount(String orgId);
}
