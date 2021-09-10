package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzBrandImplementPoint;

public interface ZjTzBrandImplementPointMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzBrandImplementPoint record);

    int insertSelective(ZjTzBrandImplementPoint record);

    ZjTzBrandImplementPoint selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzBrandImplementPoint record);

    int updateByPrimaryKey(ZjTzBrandImplementPoint record);

    List<ZjTzBrandImplementPoint> selectByZjTzBrandImplementPointList(ZjTzBrandImplementPoint record);

    int batchDeleteUpdateZjTzBrandImplementPoint(List<ZjTzBrandImplementPoint> recordList, ZjTzBrandImplementPoint record);

	int batchRecallZjTzBrandImplementPoint(List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList,ZjTzBrandImplementPoint zjTzRules);
	
	List<ZjTzBrandImplementPoint> selectZjTzHomeBrandImplementPoint(ZjTzBrandImplementPoint record);
}

