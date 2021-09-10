package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfEAccidentItem;

public interface ZxSfEAccidentItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfEAccidentItem record);

    int insertSelective(ZxSfEAccidentItem record);

    ZxSfEAccidentItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfEAccidentItem record);

    int updateByPrimaryKey(ZxSfEAccidentItem record);

    List<ZxSfEAccidentItem> selectByZxSfEAccidentItemList(ZxSfEAccidentItem record);

    int batchDeleteUpdateZxSfEAccidentItem(List<ZxSfEAccidentItem> recordList, ZxSfEAccidentItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxSfEAccidentItem> uReportYear(ZxSfEAccidentItem record);

    List<ZxSfEAccidentItem> uReportYearCom(ZxSfEAccidentItem record);
}
