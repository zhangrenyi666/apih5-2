package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightDetails;

public interface ZjXmJxQuarterlyWeightDetailsMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxQuarterlyWeightDetails record);

	int insertSelective(ZjXmJxQuarterlyWeightDetails record);

	ZjXmJxQuarterlyWeightDetails selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxQuarterlyWeightDetails record);

	int updateByPrimaryKey(ZjXmJxQuarterlyWeightDetails record);

	List<ZjXmJxQuarterlyWeightDetails> selectByZjXmJxQuarterlyWeightDetailsList(ZjXmJxQuarterlyWeightDetails record);

	int batchDeleteUpdateZjXmJxQuarterlyWeightDetails(List<ZjXmJxQuarterlyWeightDetails> recordList,
			ZjXmJxQuarterlyWeightDetails record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int batchInsertZjXmJxQuarterlyWeightDetails(List<ZjXmJxQuarterlyWeightDetails> recordList);

	int batchConfirmZjXmJxQuarterlyWeightDetails(List<ZjXmJxQuarterlyWeightDetails> recordList);

	int checkZjXmJxQuarterlyWeightDetailsConfirmStatus(ZjXmJxQuarterlyWeightDetails record);
}
