package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzOtherData;

public interface ZjTzOtherDataMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzOtherData record);

    int insertSelective(ZjTzOtherData record);

    ZjTzOtherData selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzOtherData record);

    int updateByPrimaryKey(ZjTzOtherData record);

    List<ZjTzOtherData> selectByZjTzOtherDataList(ZjTzOtherData record);

    int batchDeleteUpdateZjTzOtherData(List<ZjTzOtherData> recordList, ZjTzOtherData record);

	int batchDeleteReleaseZjTzOtherData(List<ZjTzOtherData> zjTzOtherDataList, ZjTzOtherData zjTzOtherData);

	int batchDeleteRecallZjTzOtherData(List<ZjTzOtherData> zjTzOtherDataList, ZjTzOtherData zjTzOtherData);
	
	List<ZjTzOtherData> selectZjTzHomeOtherData(ZjTzOtherData record);
}

