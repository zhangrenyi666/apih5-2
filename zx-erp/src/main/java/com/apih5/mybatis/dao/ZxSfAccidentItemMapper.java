package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfAccidentItem;

public interface ZxSfAccidentItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfAccidentItem record);

    int insertSelective(ZxSfAccidentItem record);

    ZxSfAccidentItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfAccidentItem record);

    int updateByPrimaryKey(ZxSfAccidentItem record);

    List<ZxSfAccidentItem> selectByZxSfAccidentItemList(ZxSfAccidentItem record);

    int batchDeleteUpdateZxSfAccidentItem(List<ZxSfAccidentItem> recordList, ZxSfAccidentItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSfAccidentItem> uReportForm(ZxSfAccidentItem record);

    List<ZxSfAccidentItem> uReportFormCom(ZxSfAccidentItem record);




}
