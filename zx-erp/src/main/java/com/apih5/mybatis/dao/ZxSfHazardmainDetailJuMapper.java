package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfHazardmainDetailJu;

public interface ZxSfHazardmainDetailJuMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfHazardmainDetailJu record);

    int insertSelective(ZxSfHazardmainDetailJu record);

    ZxSfHazardmainDetailJu selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfHazardmainDetailJu record);

    int updateByPrimaryKey(ZxSfHazardmainDetailJu record);

    List<ZxSfHazardmainDetailJu> selectByZxSfHazardmainDetailJuList(ZxSfHazardmainDetailJu record);

    int batchDeleteUpdateZxSfHazardmainDetailJu(List<ZxSfHazardmainDetailJu> recordList, ZxSfHazardmainDetailJu record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
