package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzBrandYearPoint;

public interface ZjTzBrandYearPointMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzBrandYearPoint record);

    int insertSelective(ZjTzBrandYearPoint record);

    ZjTzBrandYearPoint selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzBrandYearPoint record);

    int updateByPrimaryKey(ZjTzBrandYearPoint record);

    List<ZjTzBrandYearPoint> selectByZjTzBrandYearPointList(ZjTzBrandYearPoint record);

    int batchDeleteUpdateZjTzBrandYearPoint(List<ZjTzBrandYearPoint> recordList, ZjTzBrandYearPoint record);

	int batchReleaseZjTzBrandYearPoint(List<ZjTzBrandYearPoint> zjTzBrandYearPointList, ZjTzBrandYearPoint zjTzRules);

	int batchRecallZjTzBrandYearPoint(List<ZjTzBrandYearPoint> zjTzBrandYearPointList, ZjTzBrandYearPoint zjTzRules);
	
	List<ZjTzBrandYearPoint> selectZjTzHomeBrandYearPoint(ZjTzBrandYearPoint record);
}

