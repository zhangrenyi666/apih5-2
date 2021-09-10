package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightManagement;

public interface ZjXmJxQuarterlyWeightManagementMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxQuarterlyWeightManagement record);

	int insertSelective(ZjXmJxQuarterlyWeightManagement record);

	ZjXmJxQuarterlyWeightManagement selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxQuarterlyWeightManagement record);

	int updateByPrimaryKey(ZjXmJxQuarterlyWeightManagement record);

	List<ZjXmJxQuarterlyWeightManagement> selectByZjXmJxQuarterlyWeightManagementList(
			ZjXmJxQuarterlyWeightManagement record);

	int batchDeleteUpdateZjXmJxQuarterlyWeightManagement(List<ZjXmJxQuarterlyWeightManagement> recordList,
			ZjXmJxQuarterlyWeightManagement record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int countZjXmJxQuarterlyWeightManagementByCondition(ZjXmJxQuarterlyWeightManagement record);

	int deleteZjXmJxQuarterlyWeightManagementByCondition(ZjXmJxQuarterlyWeightManagement record);
}
