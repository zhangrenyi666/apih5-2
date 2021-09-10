package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryTrainingSituation;

public interface ZjXmSalaryTrainingSituationMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryTrainingSituation record);

	int insertSelective(ZjXmSalaryTrainingSituation record);

	ZjXmSalaryTrainingSituation selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryTrainingSituation record);

	int updateByPrimaryKey(ZjXmSalaryTrainingSituation record);

	List<ZjXmSalaryTrainingSituation> selectByZjXmSalaryTrainingSituationList(ZjXmSalaryTrainingSituation record);

	int batchDeleteUpdateZjXmSalaryTrainingSituation(List<ZjXmSalaryTrainingSituation> recordList,
			ZjXmSalaryTrainingSituation record);

	int batchDeleteZjXmSalaryTrainingSituationByExtensionId(List recordList, ZjXmSalaryTrainingSituation record);

}
