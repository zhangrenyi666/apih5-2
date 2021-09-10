package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxSaProjectPaySettleItem;

public interface ZxSaProjectPaySettleItemMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxSaProjectPaySettleItem record);

	int insertSelective(ZxSaProjectPaySettleItem record);

	ZxSaProjectPaySettleItem selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxSaProjectPaySettleItem record);

	int updateByPrimaryKey(ZxSaProjectPaySettleItem record);

	List<ZxSaProjectPaySettleItem> selectByZxSaProjectPaySettleItemList(ZxSaProjectPaySettleItem record);

	int batchDeleteUpdateZxSaProjectPaySettleItem(List<ZxSaProjectPaySettleItem> recordList,
			ZxSaProjectPaySettleItem record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
	int deleteAllZxSaProjectPaySettleItem(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem);

	ZxSaProjectPaySettleItem selectUpPaySettle(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem);

	List<ZxSaProjectPaySettleItem> getYgzPaySettleItemList(ZxSaProjectPaySettleItem selectPaySettleItem);

	List<ZxSaProjectPaySettleItem> selectAlreadyDoneItemList(ZxSaProjectPaySettleItem selectPaySettleItem);
}
