package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSpecialYearPoint;

public interface ZjTzSpecialYearPointMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSpecialYearPoint record);

    int insertSelective(ZjTzSpecialYearPoint record);

    ZjTzSpecialYearPoint selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSpecialYearPoint record);

    int updateByPrimaryKey(ZjTzSpecialYearPoint record);

    List<ZjTzSpecialYearPoint> selectByZjTzSpecialYearPointList(ZjTzSpecialYearPoint record);

    int batchDeleteUpdateZjTzSpecialYearPoint(List<ZjTzSpecialYearPoint> recordList, ZjTzSpecialYearPoint record);

	int batchReleaseZjTzSpecialYearPoint(List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList,
			ZjTzSpecialYearPoint zjTzRules);

	int batchRecallZjTzSpecialYearPoint(List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList,
			ZjTzSpecialYearPoint zjTzRules);
	
	List<ZjTzSpecialYearPoint> selectZjTzHomeSpecialYearPoint(ZjTzSpecialYearPoint record);
}

