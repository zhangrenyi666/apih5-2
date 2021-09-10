package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkReceivingDynamic;
import com.apih5.mybatis.pojo.ZxSkStockDifMonthItem;
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;

public interface ZxSkReceivingDynamicMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkReceivingDynamic record);

    int insertSelective(ZxSkReceivingDynamic record);

    ZxSkReceivingDynamic selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkReceivingDynamic record);

    int updateByPrimaryKey(ZxSkReceivingDynamic record);

    List<ZxSkReceivingDynamic> selectByZxSkReceivingDynamicList(ZxSkReceivingDynamic record);

    int batchDeleteUpdateZxSkReceivingDynamic(List<ZxSkReceivingDynamic> recordList, ZxSkReceivingDynamic record);

    List<ZxSkStockDifMonthItem> getReceivingDynamic (ZxSkStockDifMonthItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
