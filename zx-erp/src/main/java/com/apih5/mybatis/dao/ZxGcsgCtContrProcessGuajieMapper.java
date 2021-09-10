package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxGcsgCtContrProcessGuajie;

public interface ZxGcsgCtContrProcessGuajieMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgCtContrProcessGuajie record);

	int insertSelective(ZxGcsgCtContrProcessGuajie record);

	ZxGcsgCtContrProcessGuajie selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgCtContrProcessGuajie record);

	int updateByPrimaryKey(ZxGcsgCtContrProcessGuajie record);

	List<ZxGcsgCtContrProcessGuajie> selectByZxGcsgCtContrProcessGuajieList(ZxGcsgCtContrProcessGuajie record);

	int batchDeleteUpdateZxGcsgCtContrProcessGuajie(List<ZxGcsgCtContrProcessGuajie> recordList,
			ZxGcsgCtContrProcessGuajie record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int batchInsertZxGcsgCtContrProcessGuajie(List<ZxGcsgCtContrProcessGuajie> recordList);

	int deleteZxGcsgCtContrProcessGuajieByCondition(ZxGcsgCtContrProcessGuajie record);

	int countZxGcsgCtContrProcessGuajieByCondition(ZxGcsgCtContrProcessGuajie record);

	int updateZxGcsgCtContrProcessGuajieByCtContrApplyId(ZxGcsgCtContrProcessGuajie record);

}
