package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSafetyQualityCheckBulletin;

public interface ZjTzSafetyQualityCheckBulletinMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSafetyQualityCheckBulletin record);

    int insertSelective(ZjTzSafetyQualityCheckBulletin record);

    ZjTzSafetyQualityCheckBulletin selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSafetyQualityCheckBulletin record);

    int updateByPrimaryKey(ZjTzSafetyQualityCheckBulletin record);

    List<ZjTzSafetyQualityCheckBulletin> selectByZjTzSafetyQualityCheckBulletinList(ZjTzSafetyQualityCheckBulletin record);

    int batchDeleteUpdateZjTzSafetyQualityCheckBulletin(List<ZjTzSafetyQualityCheckBulletin> recordList, ZjTzSafetyQualityCheckBulletin record);

}

