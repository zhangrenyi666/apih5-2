package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSys;

public interface ZxGcsgCtPriceSysMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgCtPriceSys record);

	int insertSelective(ZxGcsgCtPriceSys record);

	ZxGcsgCtPriceSys selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgCtPriceSys record);

	int updateByPrimaryKey(ZxGcsgCtPriceSys record);

	List<ZxGcsgCtPriceSys> selectByZxGcsgCtPriceSysList(ZxGcsgCtPriceSys record);

	int batchDeleteUpdateZxGcsgCtPriceSys(List<ZxGcsgCtPriceSys> recordList, ZxGcsgCtPriceSys record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int cascadeDeleteZxGcsgCtPriceSysAndItem(List<ZxGcsgCtPriceSys> recordList);

	int cascadeDeleteZxGcsgCtPriceSysAndItemByCondition(ZxGcsgCtPriceSys record);

	int batchInsertZxGcsgCtPriceSys(List<ZxGcsgCtPriceSys> recordList);

	int batchUpdateZxGcsgCtPriceSys(List<ZxGcsgCtPriceSys> recordList);

	int checkZxGcsgCtPriceSysBeforeFlow(ZxGcsgCtPriceSys record);

	int updateZxGcsgCtPriceSysByCtContrApplyId(ZxGcsgCtPriceSys record);

}
