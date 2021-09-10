package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquipScrape;

public interface ZxEqEquipScrapeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquipScrape record);

    int insertSelective(ZxEqEquipScrape record);

    ZxEqEquipScrape selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquipScrape record);

    int updateByPrimaryKey(ZxEqEquipScrape record);

    List<ZxEqEquipScrape> selectByZxEqEquipScrapeList(ZxEqEquipScrape record);

    int batchDeleteUpdateZxEqEquipScrape(List<ZxEqEquipScrape> recordList, ZxEqEquipScrape record);

}

