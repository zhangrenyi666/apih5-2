package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyLibraryDetails;

public interface ZjXmJxQuarterlyLibraryDetailsMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxQuarterlyLibraryDetails record);

	int insertSelective(ZjXmJxQuarterlyLibraryDetails record);

	ZjXmJxQuarterlyLibraryDetails selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxQuarterlyLibraryDetails record);

	int updateByPrimaryKey(ZjXmJxQuarterlyLibraryDetails record);

	List<ZjXmJxQuarterlyLibraryDetails> selectByZjXmJxQuarterlyLibraryDetailsList(ZjXmJxQuarterlyLibraryDetails record);

	int batchDeleteUpdateZjXmJxQuarterlyLibraryDetails(List<ZjXmJxQuarterlyLibraryDetails> recordList,
			ZjXmJxQuarterlyLibraryDetails record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
	int batchInsertZjXmJxQuarterlyLibraryDetails(List<ZjXmJxQuarterlyLibraryDetails> recordList);

	int batchConfirmZjXmJxQuarterlyLibraryDetails(List<ZjXmJxQuarterlyLibraryDetails> recordList);

	List<ZjXmJxQuarterlyLibraryDetails> getZjXmJxQuarterlyLibraryDetailsList(ZjXmJxQuarterlyLibraryDetails record);

	int checkZjXmJxQuarterlyLibraryDetailsConfirmStatus(ZjXmJxQuarterlyLibraryDetails record);
}
