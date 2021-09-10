package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettleItem;

public interface ZxSaProjectWorkSettleItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaProjectWorkSettleItem record);

    int insertSelective(ZxSaProjectWorkSettleItem record);

    ZxSaProjectWorkSettleItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaProjectWorkSettleItem record);

    int updateByPrimaryKey(ZxSaProjectWorkSettleItem record);

    List<ZxSaProjectWorkSettleItem> selectByZxSaProjectWorkSettleItemList(ZxSaProjectWorkSettleItem record);

    int batchDeleteUpdateZxSaProjectWorkSettleItem(List<ZxSaProjectWorkSettleItem> recordList, ZxSaProjectWorkSettleItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int deleteAllZxSaProjectWorkSettleItem(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

	ZxSaProjectWorkSettleItem getCumulativeInfo(ZxSaProjectWorkSettleItem selectItem);

	ZxSaProjectWorkSettleItem selectAmtInfo(ZxSaProjectWorkSettleItem settleItem);

	List<ZxSaProjectWorkSettleItem> getYgzWorkSettleItemList(ZxSaProjectWorkSettleItem selectWorkSettleItem);
}
