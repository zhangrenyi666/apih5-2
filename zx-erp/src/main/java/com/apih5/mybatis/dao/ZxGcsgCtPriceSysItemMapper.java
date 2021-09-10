package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSysItem;

public interface ZxGcsgCtPriceSysItemMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgCtPriceSysItem record);

	int insertSelective(ZxGcsgCtPriceSysItem record);

	ZxGcsgCtPriceSysItem selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgCtPriceSysItem record);

	int updateByPrimaryKey(ZxGcsgCtPriceSysItem record);

	List<ZxGcsgCtPriceSysItem> selectByZxGcsgCtPriceSysItemList(ZxGcsgCtPriceSysItem record);

	int batchDeleteUpdateZxGcsgCtPriceSysItem(List<ZxGcsgCtPriceSysItem> recordList, ZxGcsgCtPriceSysItem record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int batchInsertZxGcsgCtPriceSysItem(List<ZxGcsgCtPriceSysItem> recordList);

	int batchDeleteZxGcsgCtPriceSysItem(List<ZxGcsgCtPriceSysItem> recordList);

	int batchUpdateZxGcsgCtPriceSysItem(List<ZxGcsgCtPriceSysItem> recordList);
}
