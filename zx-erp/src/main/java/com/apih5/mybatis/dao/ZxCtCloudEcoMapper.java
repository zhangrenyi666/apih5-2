package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtCloudEco;

public interface ZxCtCloudEcoMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxCtCloudEco record);

	int insertSelective(ZxCtCloudEco record);

	ZxCtCloudEco selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxCtCloudEco record);

	int updateByPrimaryKey(ZxCtCloudEco record);

	List<ZxCtCloudEco> selectByZxCtCloudEcoList(ZxCtCloudEco record);

	int batchDeleteUpdateZxCtCloudEco(List<ZxCtCloudEco> recordList, ZxCtCloudEco record);

	ZxCtCloudEco getZxCtCloudEcoByPrimaryKey(String key);
}
