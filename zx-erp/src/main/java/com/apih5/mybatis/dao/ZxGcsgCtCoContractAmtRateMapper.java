package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxGcsgCtCoContractAmtRate;

public interface ZxGcsgCtCoContractAmtRateMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgCtCoContractAmtRate record);

	int insertSelective(ZxGcsgCtCoContractAmtRate record);

	ZxGcsgCtCoContractAmtRate selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgCtCoContractAmtRate record);

	int updateByPrimaryKey(ZxGcsgCtCoContractAmtRate record);

	List<ZxGcsgCtCoContractAmtRate> selectByZxGcsgCtCoContractAmtRateList(ZxGcsgCtCoContractAmtRate record);

	int batchDeleteUpdateZxGcsgCtCoContractAmtRate(List<ZxGcsgCtCoContractAmtRate> recordList,
			ZxGcsgCtCoContractAmtRate record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int batchInsertZxGcsgCtCoContractAmtRate(List<ZxGcsgCtCoContractAmtRate> recordList);

	int deleteZxGcsgCtCoContractAmtRateByCondition(ZxGcsgCtCoContractAmtRate record);

}
