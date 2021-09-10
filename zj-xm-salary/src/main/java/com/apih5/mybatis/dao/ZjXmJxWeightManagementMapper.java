package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxWeightManagement;

public interface ZjXmJxWeightManagementMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxWeightManagement record);

	int insertSelective(ZjXmJxWeightManagement record);

	ZjXmJxWeightManagement selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxWeightManagement record);

	int updateByPrimaryKey(ZjXmJxWeightManagement record);

	List<ZjXmJxWeightManagement> selectByZjXmJxWeightManagementList(ZjXmJxWeightManagement record);

	int batchDeleteUpdateZjXmJxWeightManagement(List<ZjXmJxWeightManagement> recordList, ZjXmJxWeightManagement record);

	int countZjXmJxWeightManagementByYearMonth(ZjXmJxWeightManagement record);
}
