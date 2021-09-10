package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfHazardmainJu;

public interface ZxSfHazardmainJuMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfHazardmainJu record);

    int insertSelective(ZxSfHazardmainJu record);

    ZxSfHazardmainJu selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfHazardmainJu record);

    int updateByPrimaryKey(ZxSfHazardmainJu record);

    List<ZxSfHazardmainJu> selectByZxSfHazardmainJuList(ZxSfHazardmainJu record);

    int batchDeleteUpdateZxSfHazardmainJu(List<ZxSfHazardmainJu> recordList, ZxSfHazardmainJu record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
